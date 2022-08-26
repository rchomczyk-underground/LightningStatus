package com.github.shitzuu.lightningstatus.notifier.implementation;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.notifier.internal.BasedNotifier;
import eu.okaeri.hjson.JsonObject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

public class NotifierSlack extends BasedNotifier<NotifierSlackConfig> {

    public NotifierSlack(NotifierSlackConfig contextualConfig, LightningHttpClient simpleHttpClient) {
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
                    .add("text", messageContent)
                    .toString()))
                .build());
    }
}
