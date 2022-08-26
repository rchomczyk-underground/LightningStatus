package com.github.shitzuu.lightningstatus.notifier;

import com.github.shitzuu.lightningstatus.subject.MonitoredSubject;

import java.util.HashSet;
import java.util.Set;

public class NotifierController {

    private final Set<Notifier> notifiers;

    public NotifierController() {
        this.notifiers = new HashSet<>();
    }

    public void registerNotifier(Notifier notifier) {
        if (notifier.isAvailable()) {
            this.notifiers.add(notifier);
        }
    }

    private void announceMessage(String title, String messageContent) {
        this.notifiers.forEach(notifier -> notifier.sendMessage(title, messageContent));
    }

    public void announceUnaccessible(MonitoredSubject subject) {
        this.announceMessage(subject.getName(), String.format("Urgent! Service %s is currently non accessible.", subject.getName()));
    }

    public void announceUnreachable(MonitoredSubject subject) {
        this.announceMessage(subject.getName(), String.format("Urgent! Service %s is currently unreachable.", subject.getName()));
    }
}
