package pl.rosesapphire.uptimer.notifier;

import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;

public interface Notifier {

    void configure(UptimerConfig config);

    void notifyError(WatchedObject subject);

    void notifyUnreachable(WatchedObject subject);
}
