package pl.rosesapphire.uptimer.notifier.discord;

import eu.okaeri.hjson.JsonArray;
import eu.okaeri.hjson.JsonObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
public class DiscordNotifier implements Notifier<DiscordNotifierConfig, WatchedObject> {

    private final HttpClient httpClient;
    private DiscordNotifierConfig config;

    @Override
    public void configure(DiscordNotifierConfig config) {
        this.config = config;
    }

    @Override
    public void notifyError(WatchedObject subject) {
        this.sendMessage(subject, "**Urgent!**\nThere was an unexpected answer from watched service.\n\n**Perhaps that service is down at that moment or is maintained at that moment**.");
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.sendMessage(subject, "**Urgent!**\nService isn't reachable at that moment, you should instantly take care of that.");
    }

    @SneakyThrows
    @Override
    public void sendMessage(WatchedObject subject, String message) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(config.getWebhookUri()))
                .POST(BodyPublishers.ofString(new JsonObject()
                                .add("username", config.getEmbedConfig().getUsername())
                                .add("avatar_url", config.getEmbedConfig().getAvatarUrl())
                                .add("embeds", new JsonArray()
                                        .add(this.buildEmbedMessage(subject.getName(), message,
                                                config.getEmbedConfig().getColor(),
                                                config.getEmbedConfig().getFooterName(),
                                                config.getEmbedConfig().getFooterAvatarUrl())))
                        .toString()))
                .header("Content-Type", "application/json")
                .build();
        httpClient.send(httpRequest, BodyHandlers.ofString());
    }

    private JsonObject buildEmbedMessage(@NonNull String title, @NonNull String description, int color, @NonNull String footerName, @NonNull String footerAvatarUrl) {
        return new JsonObject()
                .add("title", title)
                .add("type", "rich")
                .add("description", description)
                .add("timestamp", OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString())
                .add("color", color)
                .add("footer", new JsonObject()
                        .add("text", footerName)
                        .add("icon_url", footerAvatarUrl));
    }
}
