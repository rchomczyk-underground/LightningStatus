package com.github.shitzuu.lightningstatus.watcher.implementation;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.client.LightningHttpClientException;
import com.github.shitzuu.lightningstatus.notifier.NotifierController;
import com.github.shitzuu.lightningstatus.watcher.internal.BasedCrawler;
import com.github.shitzuu.lightningstatus.subject.implementation.HttpMonitoredSubject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Set;

public class HttpCrawler extends BasedCrawler<HttpMonitoredSubject> {

    public HttpCrawler(NotifierController notifierController, LightningHttpClient simpleHttpClient, Set<HttpMonitoredSubject> subjects) {
        super(notifierController, simpleHttpClient, subjects);
    }

    @Override
    public void crawl(HttpMonitoredSubject subject) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(subject.getHost()))
                .method(subject.getMethod().name(), BodyPublishers.noBody());
        subject.getHeaders().forEach(httpRequestBuilder::header);

        NotifierController notifierController = this.getNotifierController();
        try {
            HttpResponse<Void> httpResponse = this.getSimpleHttpClient().sendHttpRequest(httpRequestBuilder.build());
            if (httpResponse.statusCode() < subject.getMinimumAcceptedCode() || httpResponse.statusCode() > subject.getMaximumAcceptedCode()) {
                notifierController.announceUnaccessible(subject);
            }
        } catch (LightningHttpClientException exception) {
            notifierController.announceUnreachable(subject);
        }
    }
}
