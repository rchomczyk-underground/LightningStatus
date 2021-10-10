package pl.rosesapphire.uptimer.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.rosesapphire.uptimer.domain.http.HttpWatchedObject;
import pl.rosesapphire.uptimer.domain.ping.PingWatchedObject;
import pl.rosesapphire.uptimer.watcher.http.HttpWatcher.HttpMethod;

import java.util.Collections;
import java.util.List;

@Getter
@Setter

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class UptimerConfig extends OkaeriConfig {

    @Variable("WEBHOOK_URI")
    @Comment("This should be the webhook to your Discord's / Slack's channel.")
    private String webhookUri = "You should put that value on your own.";

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

    @Comment("This section should contains configuration of embed message sent by rose-uptimer. (Discord)")
    private EmbedConfig embedConfig = new EmbedConfig();

    @Getter
    @Setter
    public static class EmbedConfig extends OkaeriConfig {

        @Comment("This is the username, which is displayed as author's name of message.")
        private String username = "rose-uptimer";

        @Comment("This is the avatar, which is displayed behind username.")
        private String avatarUrl = "https://avatars.githubusercontent.com/u/91547210?s=400&u=435f882d397abccb67587583b975ece92fb2d2a9&v=4";

        @Comment("This is the name displayed in the embed's footer.")
        private String footerName = "made by shitzuu with ♡";

        @Comment("This is the avatar displayed in the embed's footer.")
        private String footerAvatarUrl = "https://cdn.discordapp.com/avatars/316953327936077827/a_500ebb80d3161a98aabdf4dda4931bfc.gif";
    }
}
