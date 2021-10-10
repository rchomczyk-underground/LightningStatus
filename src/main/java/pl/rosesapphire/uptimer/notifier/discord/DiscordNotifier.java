package pl.rosesapphire.uptimer.notifier.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedTitle;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedFooter;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import pl.rosesapphire.uptimer.config.UptimerConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.time.Instant;
import java.util.concurrent.Executors;

public class DiscordNotifier implements Notifier {

    private WebhookClient webhookClient;

    @Override
    public void configure(UptimerConfig config) {
        WebhookClientBuilder webhookClientBuilder = new WebhookClientBuilder(config.getWebhookUri());
        webhookClientBuilder.setExecutorService(Executors.newSingleThreadScheduledExecutor());
        this.webhookClient = webhookClientBuilder.build();
    }

    @Override
    public void notifyError(WatchedObject subject, boolean status, int statusCode) {
        this.webhookClient.send(this.buildMessage(subject,
                "**Urgent!**\nThere was an unexpected answer from watched service, which should sent a request with code between "
                        + subject.getHttpMinimumAcceptedCode()
                        + " and "
                        + subject.getHttpMaximumAcceptedCode()
                        + ", watcher received "
                        + statusCode
                        + " which isn't in that range.\n**Perhaps that service is down at that moment or is maintained at that moment**."));
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.webhookClient.send(this.buildMessage(subject, "**Urgent!**\nService isn't reachable at that moment, you shouldn't instantly take care of that."));
    }

    private WebhookMessage buildMessage(WatchedObject subject, String description) {
        return new WebhookMessageBuilder()
                .setUsername("rose-uptimer")
                .setAvatarUrl("https://avatars.githubusercontent.com/u/91547210?s=400&u=435f882d397abccb67587583b975ece92fb2d2a9&v=4")
                .addEmbeds(new WebhookEmbedBuilder()
                        .setColor(0xFF0000)
                        .setTitle(new EmbedTitle(subject.getName(), null))
                        .setDescription(description)
                        .setFooter(new EmbedFooter("made by shitzuu with ♡", "https://images-ext-1.discordapp.net/external/5cFLzB8WP7Uadjl0QOf2QKXYgX3tcwTjkHgvr4Vu5Ao/https/cdn.discordapp.com/avatars/316953327936077827/a_500ebb80d3161a98aabdf4dda4931bfc.gif"))
                        .setTimestamp(Instant.now())
                        .build())
                .build();
    }
}
