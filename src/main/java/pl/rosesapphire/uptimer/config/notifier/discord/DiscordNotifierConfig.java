package pl.rosesapphire.uptimer.config.notifier.discord;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.rosesapphire.uptimer.config.notifier.NotifierConfig;

@Getter
@Setter

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class DiscordNotifierConfig extends NotifierConfig {

    @Comment("Status of that notifier.")
    private boolean enabled = true;

    @Variable("DISCORD_WEBHOOK_URI")
    @Comment("This should be the webhook uri.")
    private String webhookUri = "You should put that value on your own.";

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

        @Comment("This is the color of embed message.")
        private int color = 0xFF0000;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}