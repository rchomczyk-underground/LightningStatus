package com.github.shitzuu.lightningstatus.notifier.internal;

import com.github.shitzuu.lightningstatus.client.LightningHttpClient;
import com.github.shitzuu.lightningstatus.notifier.Notifier;
import eu.okaeri.configs.OkaeriConfig;

public abstract class BasedNotifier<T extends OkaeriConfig> implements Notifier {

    private final T contextualConfig;
    private final LightningHttpClient simpleHttpClient;

    protected BasedNotifier(T contextualConfig, LightningHttpClient simpleHttpClient) {
        this.contextualConfig = contextualConfig;
        this.simpleHttpClient = simpleHttpClient;
    }

    public T getContextualConfig() {
        return contextualConfig;
    }

    public LightningHttpClient getSimpleHttpClient() {
        return simpleHttpClient;
    }
}
