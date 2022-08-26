package com.github.shitzuu.lightningstatus.notifier;

public interface Notifier {

    boolean isAvailable();

    void sendMessage(String title, String messageContent);
}
