package pl.rosesapphire.uptimer.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.configs.annotation.Variable;
import lombok.Getter;
import lombok.Setter;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

import java.util.Collections;
import java.util.List;

@Getter
@Setter

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class UptimerConfig extends OkaeriConfig {

    private int delay = 15;

    @Variable("WEBHOOK_URI")
    private String webhookUri = "You should put that value on your own.";

    private List<WatchedObject> watchedObjects = List.of(
            new WatchedObject("rosesapphire's website", "https://rosesapphire.pl", HttpMethod.GET, 200, 299, Collections.emptyMap()),
            new WatchedObject("rosesapphire's storehouse", "https://storehouse.rosesapphire.pl", HttpMethod.GET, 200, 299, Collections.emptyMap())
    );
}
