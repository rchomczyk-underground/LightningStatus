package pl.rosesapphire.uptimer.watcher;

import pl.rosesapphire.uptimer.domain.WatchedObject;

public interface Watcher<T extends WatchedObject> {

    void configure();

    void watch(T subject);

    void scheduleWatching();
}
