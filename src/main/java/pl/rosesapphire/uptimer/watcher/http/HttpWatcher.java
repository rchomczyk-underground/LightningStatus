package pl.rosesapphire.uptimer.watcher.http;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.http.HttpWatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.SimpleWatcher;

import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class HttpWatcher extends SimpleWatcher<HttpWatchedObject> {

    private final UptimerConfig config;
    private final Notifier notifier;

    @Override
    public void watch(HttpWatchedObject subject) {
        HttpRequestWithBody httpRequest = Unirest.request(subject.getMethod().name(), subject.getAddress());
        httpRequest.charset(StandardCharsets.UTF_8);
        for (Entry<String, String> header : subject.getHeaders().entrySet()) {
            httpRequest.header(header.getKey(), header.getValue());
        }

        HttpResponse<?> httpResponse;
        try {
            httpResponse = httpRequest.asEmpty();
        } catch (UnirestException exception) {
            notifier.notifyUnreachable(subject);
            return;
        }

        boolean isAccessible = httpResponse.getStatus() >= subject.getMinimumAcceptedCode()
                && httpResponse.getStatus() <= subject.getMaximumAcceptedCode();
        if (isAccessible) {
            return;
        }

        notifier.notifyError(subject);
    }

    @Override
    public void scheduleWatching() {
        this.getExecutorService().scheduleAtFixedRate(() -> config.getHttpWatchedObjects()
                .forEach(this::watch), config.getDelay(), config.getDelay(), TimeUnit.MINUTES);
    }

    public enum HttpMethod {

        GET, POST, PUT, DELETE, NONE
    }
}
