package pl.rosesapphire.uptimer.watcher.http;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.Watcher;

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
        HttpRequestWithBody request = Unirest.request(subject.getHttpMethod().name(), subject.getHttpProtocol().getPrefix() + subject.getAddress()).charset(StandardCharsets.UTF_8);
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
            List<WatchedObject> watchedObjects = config.getWatchedObjects();
            for (WatchedObject watchedObject : watchedObjects) {
                this.watch(watchedObject);
            }
        }, config.getDelay(), config.getDelay(), TimeUnit.MINUTES);
    }

    @AllArgsConstructor
    @Getter
    public enum HttpProtocol {

        HTTP("http://"), HTTPS("https://"), NONE("");

        final String prefix;
    }

    public enum HttpMethod {

        GET, POST, PUT, DELETE, NONE
    }
}
