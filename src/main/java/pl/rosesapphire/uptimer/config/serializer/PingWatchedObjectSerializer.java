package pl.rosesapphire.uptimer.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.rosesapphire.uptimer.domain.ping.PingWatchedObject;

public class PingWatchedObjectSerializer implements ObjectSerializer<PingWatchedObject> {

    @Override
    public boolean supports(Class<? super PingWatchedObject> type) {
        return PingWatchedObject.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(PingWatchedObject value, SerializationData output) {
        output.add("name", value.getName());
        output.add("address", value.getAddress());
        output.add("timeout", value.getTimeout());
    }

    @Override
    public PingWatchedObject deserialize(DeserializationData input, GenericsDeclaration generics) {
        return new PingWatchedObject(
                input.get("name", String.class),
                input.get("address", String.class),
                input.get("timeout", int.class)
        );
    }
}
