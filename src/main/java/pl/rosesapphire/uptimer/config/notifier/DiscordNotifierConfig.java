package pl.rosesapphire.uptimer.config.notifier;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Variable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscordNotifierConfig extends OkaeriConfig {

    @Variable("WEBHOOK_URI")
    @Comment("This should be the webhook to your Discord's / Slack's channel.")
    private String webhookUri = "You should put that value on your own.";

    @Comment("Should that notifier be enabled?")
    private boolean enabled = true;

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