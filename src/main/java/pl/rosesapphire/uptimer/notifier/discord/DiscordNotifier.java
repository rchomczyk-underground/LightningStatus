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

    private UptimerConfig config;
    private WebhookClient webhookClient;

    @Override
    public void configure(UptimerConfig config) {
        this.config = config;
        WebhookClientBuilder webhookClientBuilder = new WebhookClientBuilder(config.getWebhookUri());
        webhookClientBuilder.setExecutorService(Executors.newSingleThreadScheduledExecutor());
        this.webhookClient = webhookClientBuilder.build();
    }

    @Override
    public void notifyError(WatchedObject subject) {
        this.webhookClient.send(this.buildMessage(subject, "**Urgent!**\nThere was an unexpected answer from watched service.\n**Perhaps that service is down at that moment or is maintained at that moment**."));
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.webhookClient.send(this.buildMessage(subject, "**Urgent!**\nService isn't reachable at that moment, you should instantly take care of that."));
    }

    private WebhookMessage buildMessage(WatchedObject subject, String description) {
        return new WebhookMessageBuilder()
                .setUsername(config.getEmbedConfig().getUsername())
                .setAvatarUrl(config.getEmbedConfig().getAvatarUrl())
                .addEmbeds(new WebhookEmbedBuilder()
                        .setColor(0xFF0000)
                        .setTitle(new EmbedTitle(subject.getName(), null))
                        .setDescription(description)
                        .setFooter(new EmbedFooter(config.getEmbedConfig().getFooterName(), config.getEmbedConfig().getFooterAvatarUrl()))
                        .setTimestamp(Instant.now())
                        .build())
                .build();
    }
}
