package com.github.shitzuu.lightningstatus.watcher;

import com.github.shitzuu.lightningstatus.subject.MonitoredSubject;

import java.util.Set;

public interface Crawler<T extends MonitoredSubject> {

    void crawl(T subject);

    void crawlAll(Set<T> subjects);
}
