package pl.rosesapphire.uptimer;

import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.config.factory.ConfigFactory;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig;
import pl.rosesapphire.uptimer.config.notifier.slack.SlackNotifierConfig;
import pl.rosesapphire.uptimer.config.serializer.HttpWatchedObjectSerializer;
import pl.rosesapphire.uptimer.config.serializer.PingWatchedObjectSerializer;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.notifier.discord.DiscordNotifier;
import pl.rosesapphire.uptimer.notifier.slack.SlackNotifier;
import pl.rosesapphire.uptimer.watcher.Watcher;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher;
import pl.rosesapphire.uptimer.watcher.ping.PingWatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {

    private final UptimerConfig config;

    public ApplicationContext() {
        this.config = new ConfigFactory(new File(System.getProperty("user.dir"))).produceConfig(UptimerConfig.class, "config.hjson",
                new HttpWatchedObjectSerializer(),
                new PingWatchedObjectSerializer());
    }

    public void initialize() {
        List<Notifier<?, WatchedObject>> notifiers = new ArrayList<>();

        if (config.getDiscordNotifierConfig().isEnabled()) {
            Notifier<DiscordNotifierConfig, WatchedObject> notifier = new DiscordNotifier();
            notifier.configure(config.getDiscordNotifierConfig());

            notifiers.add(notifier);
        }

        if (config.getSlackNotifierConfig().isEnabled()) {
            Notifier<SlackNotifierConfig, WatchedObject> notifier = new SlackNotifier();
            notifier.configure(config.getSlackNotifierConfig());

            notifiers.add(notifier);
        }

        if (notifiers.isEmpty()) {
            throw new RuntimeException("Notifiers list can not be empty.");
        }

        List.of(new HttpWatcher(config, notifiers), new PingWatcher(config, notifiers)).forEach(Watcher::configure);
    }
}
