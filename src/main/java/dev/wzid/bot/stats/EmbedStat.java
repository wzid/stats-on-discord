package dev.wzid.bot.stats;

import dev.wzid.util.BukkitUtil;
import dev.wzid.util.ConfigUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Statistic;

import java.awt.*;
import java.util.Map;

public class EmbedStat {
    private final String title;
    private final String location;
    private final String id;
    private final Statistic statistic;
    private Color embedColor;

    public EmbedStat(String title, String id, Statistic statistic) {
        this.title = title;
        this.location = "discord.stats." + id;
        this.id = id;
        this.statistic = statistic;
    }

    public void display(TextChannel textChannel, ConfigUtil data) {
        EmbedBuilder builder = getEmbed(BukkitUtil.getTopPlayersByStat(statistic));

        String messageID;
        if ((messageID = data.getConfig().getString(id)) != null) {
            textChannel.editMessageEmbedsById(messageID, builder.build()).queue();
        } else {
            textChannel.sendMessageEmbeds(builder.build()).queue(message -> {
                data.getConfig().set(id, message.getId());
                data.save();
            });
        }
    }

    public EmbedBuilder getEmbed(Map<String, Integer> data) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setFooter("by wzid 💖");
        builder.setColor(embedColor);
        return builder;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setEmbedColor(Color color) {
        this.embedColor = color;
    }
}
