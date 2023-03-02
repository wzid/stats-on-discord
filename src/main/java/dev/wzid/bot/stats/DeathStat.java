package dev.wzid.bot.stats;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.Statistic;

import java.util.Map;

public class DeathStat extends EmbedStat {

    public DeathStat() {
        super("Deaths", "deaths", Statistic.DEATHS);
    }

    @Override
    public EmbedBuilder getEmbed(Map<String, Integer> data) {
        EmbedBuilder builder = super.getEmbed(data);

        int i = 1;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            builder.addField(new MessageEmbed.Field(i + ". " + entry.getKey(), entry.getValue() + " deaths", false));
            i++;
        }

        return builder;
    }
}
