package pl.rosesapphire.uptimer.notifier;

import pl.rosesapphire.uptimer.domain.WatchedObject;

public interface Notifier<T> {

    void configure(T config);

    void notifyError(WatchedObject subject);

    void notifyUnreachable(WatchedObject subject);
}
