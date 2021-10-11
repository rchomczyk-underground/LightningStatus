package pl.rosesapphire.uptimer.notifier.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedTitle;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedFooter;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig;
import pl.rosesapphire.uptimer.config.notifier.discord.DiscordNotifierConfig.EmbedConfig;
import pl.rosesapphire.uptimer.domain.WatchedObject;
import pl.rosesapphire.uptimer.notifier.Notifier;

import java.time.Instant;
import java.util.concurrent.Executors;

public class DiscordNotifier implements Notifier<DiscordNotifierConfig> {

    private DiscordNotifierConfig config;
    private WebhookClient client;

    @Override
    public void configure(DiscordNotifierConfig config) {
        this.config = config;
        WebhookClientBuilder webhookClientBuilder = new WebhookClientBuilder(this.config.getWebhookUri());
        webhookClientBuilder.setExecutorService(Executors.newSingleThreadScheduledExecutor());
        this.client = webhookClientBuilder.build();
    }

    @Override
    public void notifyError(WatchedObject subject) {
        this.client.send(this.buildMessage(subject, "**Urgent!**\nThere was an unexpected answer from watched service.\n**Perhaps that service is down at that moment or is maintained at that moment**."));
    }

    @Override
    public void notifyUnreachable(WatchedObject subject) {
        this.client.send(this.buildMessage(subject, "**Urgent!**\nService isn't reachable at that moment, you should instantly take care of that."));
    }

    private WebhookMessage buildMessage(WatchedObject subject, String message) {
        EmbedConfig embedConfig = config.getEmbedConfig();
        return new WebhookMessageBuilder()
                .setUsername(embedConfig.getUsername())
                .setAvatarUrl(embedConfig.getAvatarUrl())
                .addEmbeds(new WebhookEmbedBuilder()
                        .setColor(0xFF0000)
                        .setTitle(new EmbedTitle(subject.getName(), null))
                        .setDescription(message)
                        .setFooter(new EmbedFooter(embedConfig.getFooterName(), embedConfig.getFooterAvatarUrl()))
                        .setTimestamp(Instant.now())
                        .build())
                .build();
    }
}
