package pl.rosesapphire.uptimer;

import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.config.factory.ConfigFactory;
import pl.rosesapphire.uptimer.config.serializer.WatchedObjectSerializer;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.notifier.discord.DiscordNotifier;
import pl.rosesapphire.uptimer.watcher.Watcher;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher;

import java.io.File;

public class ApplicationContext {

    private final UptimerConfig config;

    public ApplicationContext() {
        this.config = new ConfigFactory(new File(System.getProperty("user.dir")))
                .produceConfig(UptimerConfig.class, "config.json", new WatchedObjectSerializer());
    }

    public void initialize() {
        Notifier notifier = new DiscordNotifier();
        notifier.configure(config);

        Watcher watcher = new HttpWatcher(config, notifier);
        watcher.configure();
    }
}
