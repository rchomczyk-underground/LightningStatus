package pl.rosesapphire.uptimer.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.watcher.WatcherType;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

public class WatchedObjectSerializer implements ObjectSerializer<WatchedObject> {

    @Override
    public boolean supports(Class<? super WatchedObject> type) {
        return WatchedObject.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(WatchedObject watchedObject, SerializationData output) {
        output.add("watcher-type", WatcherType.class);
        output.add("name", watchedObject.getName());
        output.add("address", watchedObject.getAddress());
        output.add("http-method", watchedObject.getHttpMethod());
        output.add("http-minimum-accepted-code", watchedObject.getHttpMinimumAcceptedCode());
        output.add("http-maximum-accepted-code", watchedObject.getHttpMaximumAcceptedCode());
        output.addAsMap("http-headers", watchedObject.getHttpHeaders(), String.class, String.class);
    }

    @Override
    public WatchedObject deserialize(DeserializationData input, GenericsDeclaration generics) {
        return new WatchedObject(
                input.get("watcher-type", WatcherType.class),
                input.get("name", String.class),
                input.get("address", String.class),
                input.get("http-method", HttpMethod.class),
                input.get("http-minimum-accepted-code", int.class),
                input.get("http-maximum-accepted-code", int.class),
                input.getAsMap("http-headers", String.class, String.class)
        );
    }
}
