package pl.rosesapphire.uptimer.watcher.http;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.Watcher;
import pl.rosesapphire.uptimer.watcher.WatcherType;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class HttpWatcher implements Watcher {

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
        HttpRequestWithBody request = Unirest.request(subject.getHttpMethod().name(), subject.getAddress()).charset(StandardCharsets.UTF_8);
        for (Entry<String, String> httpHeader : subject.getHttpHeaders().entrySet()) {
            request.header(httpHeader.getKey(), httpHeader.getValue());
        }

        HttpResponse<?> response;
        try {
            response = request.asEmpty();
        } catch (UnirestException exception) {
            notifier.notifyUnreachable(subject);
            return;
        }

        boolean isAccessible = response.getStatus() <= subject.getHttpMaximumAcceptedCode() && response.getStatus() >= subject.getHttpMinimumAcceptedCode();
        if (isAccessible) {
            return;
        }

        notifier.notifyError(subject, false, response.getStatus());
    }

    @Override
    public void scheduleWatching() {
        this.executorService.scheduleAtFixedRate(() -> {
            List<WatchedObject> watchedObjects = config.getWatchedObjects(WatcherType.HTTP);
            for (WatchedObject watchedObject : watchedObjects) {
                this.watch(watchedObject);
            }
        }, config.getDelay(), config.getDelay(), TimeUnit.MINUTES);
    }

    public enum HttpMethod {

        GET, POST, PUT, DELETE, NONE
    }
}
