package pl.rosesapphire.uptimer.domain.ping;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.rosesapphire.uptimer.domain.WatchedObject;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PingWatchedObject extends WatchedObject {

    private final int timeout;

    public PingWatchedObject(String name, String address, int timeout) {
        super(name, address);
        this.timeout = timeout;
    }
}
