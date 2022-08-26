package com.github.shitzuu.lightningstatus.config.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.hjson.HjsonConfigurer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;

import java.io.File;

public final class ConfigFactory {

    private final File rootFile;

    public ConfigFactory(File rootFile) {
        this.rootFile = rootFile;
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, String fileName, OkaeriSerdesPack... serdesPacks) {
        return this.produceConfig(type, new File(this.rootFile, fileName), serdesPacks);
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> type, File file, OkaeriSerdesPack... serdesPacks) {
        return ConfigManager.create(type, initializer -> initializer
            .withConfigurer(new HjsonConfigurer(), serdesPacks)
            .withBindFile(file)
            .saveDefaults()
            .load(true));
    }

    public File getRootFile() {
        return rootFile;
    }
}
