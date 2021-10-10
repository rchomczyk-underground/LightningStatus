package pl.rosesapphire.uptimer.watcher;

import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.domain.WatchedObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RequiredArgsConstructor
public class SimpleWatcher<T extends WatchedObject> implements Watcher<T> {

    private ScheduledExecutorService executorService;

    @Override
    public void configure() {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.scheduleWatching();
    }

    @Override
    public void watch(T subject) {
        // There is nothing to do, this method should be overwritten.
    }

    @Override
    public void scheduleWatching() {
        // There is nothing to do, this method should be overwritten.
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
