package pl.rosesapphire.uptimer.notifier.discord;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kong.unirest.Unirest;
import lombok.NonNull;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DiscordNotifier implements Notifier<DiscordNotifierConfig, WatchedObject> {

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

    @Override
    public void sendMessage(WatchedObject subject, String message) {
        JsonObject webhookObject = new JsonObject();
        webhookObject.addProperty("username", config.getEmbedConfig().getUsername());
        webhookObject.addProperty("avatar_url", config.getEmbedConfig().getAvatarUrl());

        JsonArray embedsArray = new JsonArray();
        embedsArray.add(this.buildEmbedMessage(subject.getName(), message,
                config.getEmbedConfig().getColor(),
                config.getEmbedConfig().getFooterName(),
                config.getEmbedConfig().getFooterAvatarUrl()));

        webhookObject.add("embeds", embedsArray);

        Unirest.post(config.getWebhookUri())
                .charset(StandardCharsets.UTF_8)
                .header("Content-Type", "application/json")
                .body(webhookObject.toString())
                .asEmpty();
    }

    private JsonObject buildEmbedMessage(@NonNull String title,
                                         @NonNull String description,
                                         int color,
                                         @NonNull String footerName,
                                         @NonNull String footerAvatarUrl) {
        JsonObject embedObject = new JsonObject();
        embedObject.addProperty("title", title);
        embedObject.addProperty("type", "rich");
        embedObject.addProperty("description", description);
        embedObject.addProperty("timestamp", OffsetDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")).toString());
        embedObject.addProperty("color", color);

        JsonObject footerObject = new JsonObject();
        footerObject.addProperty("text", footerName);
        footerObject.addProperty("icon_url", footerAvatarUrl);

        embedObject.add("footer", footerObject);
        return embedObject;
    }
}
