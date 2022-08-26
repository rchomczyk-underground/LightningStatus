package com.github.shitzuu.lightningstatus.subject.implementation;

import com.github.shitzuu.lightningstatus.subject.MonitoredSubject;

public class PingMonitoredSubject extends MonitoredSubject {

    private final int timeout;

    public PingMonitoredSubject(String name, String host, int timeout) {
        super(name, host);
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }
}
