package pl.rosesapphire.uptimer.domain;

import lombok.Data;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpProtocol;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

import java.util.Map;

@Data
public class WatchedObject {

    private final String name;
    private final String address;

    private final HttpMethod httpMethod;
    private final HttpProtocol httpProtocol;
    private final int httpMinimumAcceptedCode;
    private final int httpMaximumAcceptedCode;
    private final Map<String, String> httpHeaders;
}
