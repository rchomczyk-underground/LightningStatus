package pl.rosesapphire.uptimer.notifier.slack;

import eu.okaeri.hjson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.rosesapphire.uptimer.config.notifier.slack.SlackNotifierConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

@RequiredArgsConstructor
public class SlackNotifier implements Notifier<SlackNotifierConfig, WatchedObject> {

    private final HttpClient httpClient;
    private SlackNotifierConfig config;

    @Override
    public void configure(SlackNotifierConfig config) {
        this.config = config;
    }

    @Override
    public void notifyError(WatchedObject subject) {
        this.sendMessage("Urgent!\nThere was an unexpected answer from watched service (" + subject.getName() + ").\n**Perhaps that service is down at that moment or is maintained at that moment**.");
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.sendMessage("Urgent!\nService (" + subject.getName() + ") isn't reachable at that moment, you should instantly take care of that.");
    }

    @SneakyThrows
    @Override
    public void sendMessage(String message) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getWebhookUri()))
                .POST(BodyPublishers.ofString(new JsonObject()
                        .add("text", message)
                        .toString()))
                .header("Content-Type", "application/json")
                .build();
        httpClient.send(httpRequest, BodyHandlers.ofString());
    }
}
