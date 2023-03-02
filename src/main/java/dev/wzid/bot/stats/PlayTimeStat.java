package dev.wzid.bot.stats;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Statistic;

import java.util.Map;

public class PlayTimeStat extends EmbedStat {

    public PlayTimeStat() {
        super("Play Time", "play-time", Statistic.PLAY_ONE_MINUTE);
    }

    @Override
    public EmbedBuilder getEmbed(Map<String, Integer> data) {
        EmbedBuilder builder = super.getEmbed(data);

        int i = 1;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int hours = entry.getValue() / (3600 * 20);

            // Make sure that if there is more than 1 hour than we do not compute that into the minutes
            int minutes = hours > 0 ? ((entry.getValue() - (hours * 3600)) / 60) : entry.getValue() / 60;

            builder.addField(new MessageEmbed.Field(i + ". " + entry.getKey(), hours + " hours and " + minutes + " minutes", false));
            i++;
        }

        return builder;
    }
}
