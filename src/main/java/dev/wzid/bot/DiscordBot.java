package dev.wzid.bot;

import dev.wzid.bot.stats.PlayTimeStat;
import dev.wzid.StatisticsViewer;
import dev.wzid.bot.stats.DamageDealtStat;
import dev.wzid.bot.stats.DeathStat;
import dev.wzid.bot.stats.JumpStat;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.util.concurrent.*;

public class DiscordBot {
    private final BotConfig botConfig;
    private final String token;
    private Color embedColor;

    //Here we load the bot and use the configuration file to get all necessary data
    public DiscordBot() throws MissingTokenException {
        botConfig = new BotConfig("bot-properties.yml");
        token = botConfig.getConfig().getString("bot.token");
        String hex = botConfig.getConfig().getString("discord.embed-color");

        // Make sure that the user has specified a bot token
        if (token == null || token.isEmpty() || token.equals("ID")) {
            throw new MissingTokenException();
        }
    }

    public void initializeBot(StatisticsViewer plugin) throws InvalidConfigException, InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(token)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS);

        /* Finally we build the Discord bot and wait until it is connected
        If the bot is not able to log in then it will throw an exception */
        JDA jda = builder.build().awaitReady();

        plugin.setJDA(jda);

        String channelID = botConfig.getConfig().getString("discord.stats.channel-id");
        plugin.getLogger().info(channelID);

        if (channelID == null) {
            throw new InvalidConfigException("The statistics channel ID is missing!");
        }

        TextChannel statChannel = jda.getTextChannelById(channelID);
        if (statChannel == null) {
            throw new InvalidConfigException("The specified statistics channel ID is invalid!");
        }

        if (isStatEnabled(botConfig.getConfig(), "play-time")) {
            plugin.getStatistics().add(new PlayTimeStat());
        }
        if (isStatEnabled(botConfig.getConfig(), "deaths")) {
            plugin.getStatistics().add(new DeathStat());
        }
        if (isStatEnabled(botConfig.getConfig(), "damage-dealt")) {
            plugin.getStatistics().add(new DamageDealtStat());
        }
        if (isStatEnabled(botConfig.getConfig(), "jumps")) {
            plugin.getStatistics().add(new JumpStat());
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(new BotScheduledEvent(botConfig.getConfigUtil()), 0, 5, TimeUnit.MINUTES);

    }

    private boolean isStatEnabled(FileConfiguration config, String id) {
        return config.getBoolean("discord.stats." + id);
    }
}
