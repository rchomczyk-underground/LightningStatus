package pl.rosesapphire.uptimer.watcher;

import pl.rosesapphire.uptimer.domain.WatchedObject;

public interface Watcher {

    void configure();

    void watch(WatchedObject subject);

    void scheduleWatching();
}
