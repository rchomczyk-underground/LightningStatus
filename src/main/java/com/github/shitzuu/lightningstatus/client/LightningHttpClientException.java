package com.github.shitzuu.lightningstatus.client;

public class LightningHttpClientException extends IllegalStateException {

    public LightningHttpClientException(String message, Throwable exception) {
        super(message, exception);
    }
}
