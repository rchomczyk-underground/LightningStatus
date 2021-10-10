package pl.rosesapphire.uptimer.domain.http;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
public class HttpWatchedObject extends WatchedObject {

    private final HttpMethod method;
    private final int minimumAcceptedCode;
    private final int maximumAcceptedCode;
    private final Map<String, String> headers;

    public HttpWatchedObject(String name, String address, HttpMethod method, int minimumAcceptedCode, int maximumAcceptedCode, Map<String, String> headers) {
        super(name, address);
        this.method = method;
        this.minimumAcceptedCode = minimumAcceptedCode;
        this.maximumAcceptedCode = maximumAcceptedCode;
        this.headers = headers;
    }
}
