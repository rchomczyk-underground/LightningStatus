package com.github.shitzuu.lightningstatus.config.serializer;

import com.github.shitzuu.lightningstatus.subject.implementation.HttpMonitoredSubject;
import com.github.shitzuu.lightningstatus.watcher.implementation.HttpCrawlerMethod;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

public class HttpMonitoredSubjectSerializer implements ObjectSerializer<HttpMonitoredSubject> {

    @Override
    public boolean supports(Class<? super HttpMonitoredSubject> type) {
        return HttpMonitoredSubject.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(HttpMonitoredSubject value, SerializationData output, GenericsDeclaration generics) {
        output.add("name", value.getName());
        output.add("host", value.getHost());
        output.add("method", value.getMethod());
        output.add("minimumAcceptedCode", value.getMinimumAcceptedCode());
        output.add("maximumAcceptedCode", value.getMaximumAcceptedCode());
        output.addAsMap("headers", value.getHeaders(), String.class, String.class);
    }

    @Override
    public HttpMonitoredSubject deserialize(DeserializationData input, GenericsDeclaration generics) {
        return new HttpMonitoredSubject(
            input.get("name", String.class),
            input.get("host", String.class),
            input.get("method", HttpCrawlerMethod.class),
            input.get("minimumAcceptedCode", int.class),
            input.get("maximumAcceptedCode", int.class),
            input.getAsMap("headers", String.class, String.class));
    }
}
