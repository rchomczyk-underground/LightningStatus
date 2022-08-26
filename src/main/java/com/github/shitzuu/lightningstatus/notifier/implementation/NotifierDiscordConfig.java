package com.github.shitzuu.lightningstatus.notifier.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.configs.annotation.Variable;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class NotifierDiscordConfig extends OkaeriConfig {

    @Comment("Whether the notifier should be enabled.")
    public boolean enabled = false;

    @Variable("DISCORD_WEBHOOK_URI")
    @Comment("This should be the webhook uri.")
    public String webhookUri = "You should put that value on your own.";

    @Comment("This section should contains configuration of embed message sent by rose-uptimer. (Discord)")
    public EmbedConfig embedConfig = new EmbedConfig();

    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class EmbedConfig extends OkaeriConfig {

        @Comment("This is the color of embed message.")
        public int color = 0xFF0000;

        @Comment("This is the username, which is displayed as author's name of message.")
        public String username = "LightningStatus";

        @Comment("This is the avatar, which is displayed behind username.")
        public String avatarUrl = "https://avatars.githubusercontent.com/u/63915083?s=400&u=e63bfb2398fc37a8c30e3cbbdd571c870a506c0a&v=4";

        @Comment("This is the name displayed in the embed's footer.")
        public String footerName = "Powered by shitzuu/LightningStatus";

        @Comment("This is the avatar displayed in the embed's footer.")
        public String footerAvatarUrl = "https://avatars.githubusercontent.com/u/63915083?s=400&u=e63bfb2398fc37a8c30e3cbbdd571c870a506c0a&v=4";
    }
}