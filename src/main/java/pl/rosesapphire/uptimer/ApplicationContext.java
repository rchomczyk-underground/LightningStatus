package pl.rosesapphire.uptimer;

import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.config.factory.ConfigFactory;
import pl.rosesapphire.uptimer.config.serializer.HttpWatchedObjectSerializer;
import pl.rosesapphire.uptimer.config.serializer.PingWatchedObjectSerializer;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.notifier.discord.DiscordNotifier;
import pl.rosesapphire.uptimer.watcher.Watcher;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher;
import pl.rosesapphire.uptimer.watcher.ping.PingWatcher;

import java.io.File;
import java.util.List;

public class ApplicationContext {

    private final UptimerConfig config;

    public ApplicationContext() {
        this.config = new ConfigFactory(new File(System.getProperty("user.dir"))).produceConfig(UptimerConfig.class, "config.hjson",
                new HttpWatchedObjectSerializer(),
                new PingWatchedObjectSerializer());
    }

    public void initialize() {
        Notifier notifier = new DiscordNotifier();
        notifier.configure(config);

        List.of(new HttpWatcher(config, notifier), new PingWatcher(config, notifier)).forEach(Watcher::configure);
    }
}
