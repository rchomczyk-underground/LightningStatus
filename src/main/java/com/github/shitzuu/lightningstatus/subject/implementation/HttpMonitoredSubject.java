package com.github.shitzuu.lightningstatus.subject.implementation;

import com.github.shitzuu.lightningstatus.subject.MonitoredSubject;
import com.github.shitzuu.lightningstatus.crawler.implementation.HttpCrawlerMethod;

import java.util.Map;

public class HttpMonitoredSubject extends MonitoredSubject {

    private final HttpCrawlerMethod method;
    private final int minimumAcceptedCode;
    private final int maximumAcceptedCode;
    private final Map<String, String> headers;

    public HttpMonitoredSubject(String name, String host, HttpCrawlerMethod method, int minimumAcceptedCode, int maximumAcceptedCode, Map<String, String> headers) {
        super(name, host);
        this.method = method;
        this.minimumAcceptedCode = minimumAcceptedCode;
        this.maximumAcceptedCode = maximumAcceptedCode;
        this.headers = headers;
    }

    public HttpCrawlerMethod getMethod() {
        return method;
    }

    public int getMinimumAcceptedCode() {
        return minimumAcceptedCode;
    }

    public int getMaximumAcceptedCode() {
        return maximumAcceptedCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
