package com.github.shitzuu.lightningstatus.watcher.implementation;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.notifier.NotifierController;
import com.github.shitzuu.lightningstatus.subject.implementation.PingMonitoredSubject;
import com.github.shitzuu.lightningstatus.watcher.internal.BasedCrawler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Set;

public class PingCrawler extends BasedCrawler<PingMonitoredSubject> {

    public PingCrawler(NotifierController notifierController, LightningHttpClient simpleHttpClient, Set<PingMonitoredSubject> subjects) {
        super(notifierController, simpleHttpClient, subjects);
    }

    @Override
    public void crawl(PingMonitoredSubject subject) {
        NotifierController notifierController = this.getNotifierController();
        try {
            // Attempting to resolve the machine by specified
            // host / hostname, if it fails, then we assume
            // that the service is unreachable at that moment.
            if (InetAddress.getByName(subject.getHost()).isReachable(subject.getTimeout())) {
                return;
            }

            notifierController.announceUnaccessible(subject);
        } catch (IOException exception) {
            notifierController.announceUnreachable(subject);
        }
    }
}
