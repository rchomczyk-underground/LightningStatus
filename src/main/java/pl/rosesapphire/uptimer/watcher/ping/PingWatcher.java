package pl.rosesapphire.uptimer.watcher.ping;

import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.domain.ping.PingWatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.SimpleWatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PingWatcher extends SimpleWatcher<PingWatchedObject> {

    private final UptimerConfig config;
    private final List<Notifier<?, WatchedObject>> notifiers;

    @Override
    public void watch(PingWatchedObject subject) {
        try {
            InetAddress inetAddress = InetAddress.getByName(subject.getAddress());

            boolean reachable = inetAddress.isReachable(subject.getTimeout());
            if (reachable) {
                return;
            }

            notifiers.forEach(notifier -> notifier.notifyError(subject));
        } catch (IOException exception) {
            notifiers.forEach(notifier -> notifier.notifyUnreachable(subject));
        }
    }

    @Override
    public void scheduleWatching() {
        this.getExecutorService().scheduleAtFixedRate(() -> config.getPingWatchedObjects()
                .forEach(this::watch), config.getDelay(), config.getDelay(), TimeUnit.MINUTES);
    }
}
