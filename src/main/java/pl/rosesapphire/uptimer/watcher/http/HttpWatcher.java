package pl.rosesapphire.uptimer.watcher.http;

import lombok.RequiredArgsConstructor;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.domain.http.HttpWatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;
import pl.rosesapphire.uptimer.watcher.SimpleWatcher;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class HttpWatcher extends SimpleWatcher<HttpWatchedObject> {

    private final UptimerConfig config;
    private final HttpClient httpClient;
    private final List<Notifier<?, WatchedObject>> notifiers;

    @Override
    public void watch(HttpWatchedObject subject) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(subject.getAddress()))
                .method(subject.getMethod().name(), BodyPublishers.noBody());
        for (Entry<String, String> headerEntry : subject.getHeaders().entrySet()) {
            httpRequestBuilder.header(headerEntry.getKey(), headerEntry.getValue());
        }

        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequestBuilder.build(), BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            notifiers.forEach(notifier -> notifier.notifyUnreachable(subject));
            return;
        }

        boolean isAccessible = httpResponse.statusCode() >= subject.getMinimumAcceptedCode() && httpResponse.statusCode() <= subject.getMaximumAcceptedCode();
        if (isAccessible) {
            return;
        }

        notifiers.forEach(notifier -> notifier.notifyError(subject));
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
