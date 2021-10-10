package pl.rosesapphire.uptimer.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import pl.rosesapphire.uptimer.domain.http.HttpWatchedObject;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

public class HttpWatchedObjectSerializer implements ObjectSerializer<HttpWatchedObject> {

    @Override
    public boolean supports(Class<? super HttpWatchedObject> type) {
        return HttpWatchedObject.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(HttpWatchedObject value, SerializationData output) {
        output.add("name", value.getName());
        output.add("address", value.getAddress());
        output.add("method", value.getMethod());
        output.add("minimumAcceptedCode", value.getMinimumAcceptedCode());
        output.add("maximumAcceptedCode", value.getMaximumAcceptedCode());
        output.addAsMap("headers", value.getHeaders(), String.class, String.class);
    }

    @Override
    public HttpWatchedObject deserialize(DeserializationData input, GenericsDeclaration generics) {
        return new HttpWatchedObject(
                input.get("name", String.class),
                input.get("address", String.class),
                input.get("method", HttpMethod.class),
                input.get("minimumAcceptedCode", int.class),
                input.get("maximumAcceptedCode", int.class),
                input.getAsMap("headers", String.class, String.class)
        );
    }
}
