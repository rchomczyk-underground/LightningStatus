package pl.rosesapphire.uptimer.config.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.hjson.HjsonConfigurer;
import eu.okaeri.configs.serdes.ObjectSerializer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Arrays;

@RequiredArgsConstructor
public final class ConfigFactory {

    private final File directory;

    public <T extends OkaeriConfig> T produceConfig(@NonNull Class<T> type, @NonNull String fileName, @NonNull ObjectSerializer<?>... serializers) {
        return this.produceConfig(type, new File(this.directory, fileName), serializers);
    }

    public <T extends OkaeriConfig> T produceConfig(@NonNull Class<T> type, @NonNull File file, @NonNull ObjectSerializer<?>... serializers) {
        return ConfigManager.create(type, initializer -> initializer
                .withConfigurer(new HjsonConfigurer(), registry -> Arrays.stream(serializers).forEach(registry::register))
                .withBindFile(file)
                .saveDefaults()
                .load(true));
    }
}
