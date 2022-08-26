package com.github.shitzuu.lightningstatus.config.serializer;

import com.github.shitzuu.lightningstatus.subject.implementation.PingMonitoredSubject;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

public class PingMonitoredSubjectSerializer implements ObjectSerializer<PingMonitoredSubject> {

    @Override
    public boolean supports(Class<? super PingMonitoredSubject> type) {
        return PingMonitoredSubject.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(PingMonitoredSubject value, SerializationData output, GenericsDeclaration generics) {
        output.add("name", value.getName());
        output.add("host", value.getHost());
        output.add("timeout", value.getTimeout());
    }

    @Override
    public PingMonitoredSubject deserialize(DeserializationData input, GenericsDeclaration generics) {
        return new PingMonitoredSubject(
            input.get("name", String.class),
            input.get("host", String.class),
            input.get("timeout", int.class));
    }
}
