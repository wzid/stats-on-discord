package dev.wzid.bot;

import dev.wzid.util.ConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BotConfig {

    private final ConfigUtil config;

    public BotConfig(String plugin) {
        FileConfiguration defaultConfig = new YamlConfiguration();

        defaultConfig.set("bot.token", "ID");


        defaultConfig.set("discord.embed-color", "#dd447f");

        defaultConfig.set("discord.stats.channel-id", "ID");
        defaultConfig.set("discord.stats.play-time", true);
        defaultConfig.set("discord.stats.deaths", true);
        defaultConfig.set("discord.stats.damage-dealt", true);
        defaultConfig.set("discord.stats.jumps", false);

        config = new ConfigUtil(plugin);

        config.getConfig().setDefaults(defaultConfig);
        config.getConfig().options().copyDefaults(true);
        config.save();
    }

    public FileConfiguration getConfig() {
        return config.getConfig();
    }

    public ConfigUtil getConfigUtil() {
        return config;
    }

    public void save() {
        config.save();
    }
}
