package dev.wzid.bot;

import dev.wzid.bot.stats.EmbedStat;
import dev.wzid.StatisticsViewer;
import dev.wzid.util.ConfigUtil;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class BotScheduledEvent implements Runnable {
    private final String statisticsChannelID;
    private final ConfigUtil discordData;
    private final Color embedColor;

    public BotScheduledEvent(ConfigUtil config) {
        this.statisticsChannelID = config.getConfig().getString("discord.stats.channel-id");
        this.discordData = new ConfigUtil("discord-data.yml");
        String hex = config.getConfig().getString("discord.embed-color");

        if (hex == null)
            embedColor = new Color(-1);
        else
            embedColor = Color.decode(hex);
    }

    @Override
    public void run() {
        TextChannel textChannel = StatisticsViewer.INSTANCE.getJDA().getTextChannelById(statisticsChannelID);
        if (textChannel == null)
            return;
        for (EmbedStat stat : StatisticsViewer.INSTANCE.getStatistics()) {
            stat.setEmbedColor(embedColor);
            stat.display(textChannel, discordData);
        }
    }
}
