package com.github.shitzuu.lightningstatus.client;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class LightningHttpClient {

    private final HttpClient httpClient;

    public LightningHttpClient() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMinutes(30))
            .build();
    }

    public HttpResponse<Void> sendHttpRequest(HttpRequest request) {
        try {
            return this.httpClient.send(request, BodyHandlers.discarding());
        } catch (IOException | InterruptedException exception) {
            throw new LightningHttpClientException(
                "Couldn't send the http request through the lightning http client.", exception);
        }
    }
}
