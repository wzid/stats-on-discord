package dev.wzid.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    private static String defaultPath;
    private final File file;
    private final FileConfiguration config;


    public ConfigUtil(String path) {
        this.file = new File(defaultPath + '/' + path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultPath(String defaultPath) {
        ConfigUtil.defaultPath = defaultPath;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
