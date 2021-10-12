package pl.rosesapphire.uptimer.config.notifier.slack;

import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.rosesapphire.uptimer.config.notifier.NotifierConfig;

@Getter
@Setter

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class SlackNotifierConfig extends NotifierConfig {

    @Comment("Status of that notifier.")
    private boolean enabled = true;

    @Variable("SLACK_WEBHOOK_URI")
    @Comment("This should be the webhook uri.")
    private String webhookUri = "You should put that value on your own.";

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
