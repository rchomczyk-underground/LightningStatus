package com.github.shitzuu.lightningstatus.notifier.implementation;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.notifier.internal.BasedNotifier;
import eu.okaeri.hjson.JsonArray;
import eu.okaeri.hjson.JsonObject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class NotifierDiscord extends BasedNotifier<NotifierDiscordConfig> {

    public NotifierDiscord(NotifierDiscordConfig contextualConfig, LightningHttpClient simpleHttpClient) {
        super(contextualConfig, simpleHttpClient);
    }

    @Override
    public boolean isAvailable() {
        return this.getContextualConfig().enabled;
    }

    @Override
    public void sendMessage(String title, String messageContent) {
        this.getSimpleHttpClient().sendHttpRequest(
            HttpRequest.newBuilder()
                .uri(URI.create(this.getContextualConfig().webhookUri))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(new JsonObject()
                    .add("username", this.getContextualConfig().embedConfig.username)
                    .add("avatar_url", this.getContextualConfig().embedConfig.avatarUrl)
                    .add("embeds", new JsonArray()
                        .add(this.buildEmbedMessage(title, messageContent,
                            this.getContextualConfig().embedConfig.color,
                            this.getContextualConfig().embedConfig.footerName,
                            this.getContextualConfig().embedConfig.footerAvatarUrl)))
                    .toString()))
                .build());
    }

    private JsonObject buildEmbedMessage(String title, String description,
                                         int color,
                                         String footerName, String footerAvatarUrl) {
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
