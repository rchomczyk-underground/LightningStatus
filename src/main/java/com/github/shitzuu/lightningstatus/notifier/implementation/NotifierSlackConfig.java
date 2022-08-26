package com.github.shitzuu.lightningstatus.notifier.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class NotifierSlackConfig extends OkaeriConfig {

    @Comment("Whether the notifier should be enabled.")
    public boolean enabled = false;

    @Variable("SLACK_WEBHOOK_URI")
    @Comment("This should be the webhook uri.")
    public String webhookUri = "You should put that value on your own.";
}
