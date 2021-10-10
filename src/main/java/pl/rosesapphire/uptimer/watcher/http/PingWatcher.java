package pl.rosesapphire.uptimer.watcher.http;

import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.Watcher;
import pl.rosesapphire.uptimer.watcher.WatcherType;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PingWatcher implements Watcher {

    private final UptimerConfig config;
    private final Notifier notifier;

    private ScheduledExecutorService executorService;

    @Override
    public void configure() {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.scheduleWatching();
    }

    @Override
    public void watch(WatchedObject subject) {
        // todo: implement that
    }

    @Override
    public void scheduleWatching() {
        this.executorService.scheduleAtFixedRate(() -> {
            List<WatchedObject> watchedObjects = config.getWatchedObjects(WatcherType.PING);
            for (WatchedObject watchedObject : watchedObjects) {
                this.watch(watchedObject);
            }
        }, config.getDelay(), config.getDelay(), TimeUnit.MINUTES);
    }
}
