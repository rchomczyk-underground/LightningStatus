package pl.rosesapphire.uptimer.notifier.slack;

import com.google.gson.JsonObject;
import kong.unirest.Unirest;
import pl.rosesapphire.uptimer.config.notifier.slack.SlackNotifierConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.nio.charset.StandardCharsets;

public class SlackNotifier implements Notifier<SlackNotifierConfig> {

    private SlackNotifierConfig config;

    @Override
    public void configure(SlackNotifierConfig config) {
        this.config = config;
    }

    @Override
    public void notifyError(WatchedObject subject) {
        this.sendMessage("Urgent!\nThere was an unexpected answer from watched service (" + subject.getName() + ").\n**Perhaps that service is down at that moment or is maintained at that moment**.");
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.sendMessage("Urgent!\nService (" + subject.getName() + ") isn't reachable at that moment, you should instantly take care of that.");
    }

    public void sendMessage(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);

        Unirest.post(config.getWebhookUri())
                .charset(StandardCharsets.UTF_8)
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .asEmpty();
    }
}
