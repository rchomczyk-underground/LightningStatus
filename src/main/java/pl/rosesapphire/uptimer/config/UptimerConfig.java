package pl.rosesapphire.uptimer.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig;
import pl.rosesapphire.uptimer.config.notifier.slack.SlackNotifierConfig;
import pl.rosesapphire.uptimer.domain.http.HttpWatchedObject;
import pl.rosesapphire.uptimer.domain.ping.PingWatchedObject;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

import java.util.Collections;
import java.util.List;

@Getter
@Setter

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class UptimerConfig extends OkaeriConfig {

    @Comment("This should be delay of checking status of watchable objects. (in minutes)")
    private int delay = 15;

    @Comment("This should be the list of watchable objects which should be checked by HttpWatcher.")
    private List<HttpWatchedObject> httpWatchedObjects = List.of(
            new HttpWatchedObject("rosesapphire's website", "https://rosesapphire.pl", HttpMethod.GET, 200, 299, Collections.emptyMap()),
            new HttpWatchedObject("rosesapphire's storehouse", "https://storehouse.rosesapphire.pl", HttpMethod.GET, 200, 299, Collections.emptyMap())
    );

    @Comment("This should be the list of watchable objects which should be checked by PingWatcher.")
    private List<PingWatchedObject> pingWatchedObjects = List.of(
            new PingWatchedObject("rosesapphire's machine", "n1.rosesapphire.pl", 5000)
    );


    @Comment("This section should contain configuration for discord notifier.")
    private DiscordNotifierConfig discordNotifierConfig = new DiscordNotifierConfig();

    @Comment("This section should contain configuration for slack notifier.")
    private SlackNotifierConfig slackNotifierConfig = new SlackNotifierConfig();
}
