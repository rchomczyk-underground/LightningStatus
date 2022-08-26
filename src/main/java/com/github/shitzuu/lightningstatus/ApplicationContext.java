package com.github.shitzuu.lightningstatus;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.config.PrimaryConfig;
import com.github.shitzuu.lightningstatus.config.factory.ConfigFactory;
import com.github.shitzuu.lightningstatus.config.serializer.HttpMonitoredSubjectSerializer;
import com.github.shitzuu.lightningstatus.config.serializer.PingMonitoredSubjectSerializer;
import com.github.shitzuu.lightningstatus.notifier.NotifierController;
import com.github.shitzuu.lightningstatus.notifier.implementation.NotifierDiscord;
import com.github.shitzuu.lightningstatus.notifier.implementation.NotifierDiscordConfig;
import com.github.shitzuu.lightningstatus.notifier.implementation.NotifierSlack;
import com.github.shitzuu.lightningstatus.notifier.implementation.NotifierSlackConfig;
import com.github.shitzuu.lightningstatus.watcher.implementation.HttpCrawler;
import com.github.shitzuu.lightningstatus.watcher.implementation.PingCrawler;
import com.github.shitzuu.lightningstatus.watcher.internal.BasedCrawler;
import eu.okaeri.configs.serdes.commons.SerdesCommons;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ApplicationContext implements ApplicationLifecycle {

    private final LightningHttpClient simpleHttpClient =
        new LightningHttpClient();
    private final ScheduledExecutorService executorService =
        Executors.newScheduledThreadPool(4);

    private final ConfigFactory configFactory;

    public ApplicationContext() {
        this.configFactory = new ConfigFactory(new File(System.getProperty("user.dir")));
    }

    @Override
    public void construct() {
        NotifierController notifierController = new NotifierController();
        notifierController.registerNotifier(new NotifierDiscord(
            this.configFactory.produceConfig(NotifierDiscordConfig.class,
                new File(this.getNotifierFile(), "discord.hjson")),
            this.simpleHttpClient));
        notifierController.registerNotifier(new NotifierSlack(
            this.configFactory.produceConfig(NotifierSlackConfig.class,
                new File(this.getNotifierFile(), "slack.hjson")),
            this.simpleHttpClient));

        PrimaryConfig primaryConfig = this.configFactory.produceConfig(PrimaryConfig.class, "config.hjson",
            new SerdesCommons(), registry -> {
                registry.register(new HttpMonitoredSubjectSerializer());
                registry.register(new PingMonitoredSubjectSerializer());
            });

        List<BasedCrawler<?>> crawlers = List.of(
            new HttpCrawler(notifierController, this.simpleHttpClient,
                primaryConfig.httpMonitoredSubjects.stream().collect(Collectors.toUnmodifiableSet())),
            new PingCrawler(notifierController, this.simpleHttpClient,
                primaryConfig.pingMonitoredSubjects.stream().collect(Collectors.toUnmodifiableSet())));
        crawlers.forEach(crawler -> this.executorService.scheduleAtFixedRate(crawler::crawlSubjects,
            primaryConfig.monitorInterval.toMillis(),
            primaryConfig.monitorInterval.toMillis(), TimeUnit.MILLISECONDS));
    }

    private File getNotifierFile() {
        return new File(this.configFactory.getRootFile(), "notifiers");
    }
}
