package com.github.shitzuu.lightningstatus.crawler.internal;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.notifier.NotifierController;
import com.github.shitzuu.lightningstatus.subject.MonitoredSubject;
import com.github.shitzuu.lightningstatus.crawler.Crawler;

import java.util.Collections;
import java.util.Set;

public abstract class BasedCrawler<T extends MonitoredSubject> implements Crawler<T> {

    private final NotifierController notifierController;
    private final LightningHttpClient simpleHttpClient;
    private final Set<T> subjects;

    protected BasedCrawler(NotifierController notifierController, LightningHttpClient simpleHttpClient, Set<T> subjects) {
        this.notifierController = notifierController;
        this.simpleHttpClient = simpleHttpClient;
        this.subjects = subjects;
    }

    @Override
    public void crawlAll(Set<T> subjects) {
        subjects.forEach(this::crawl);
    }

    public void crawlSubjects() {
        this.crawlAll(this.resolveSubjects());
    }

    public NotifierController getNotifierController() {
        return notifierController;
    }

    public LightningHttpClient getSimpleHttpClient() {
        return simpleHttpClient;
    }

    public Set<T> resolveSubjects() {
        return Collections.unmodifiableSet(this.subjects);
    }
}
