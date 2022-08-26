package com.github.shitzuu.lightningstatus.subject;

public class MonitoredSubject {

    private final String name;
    private final String host;

    public MonitoredSubject(String name, String host) {
        this.name = name;
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }
}
