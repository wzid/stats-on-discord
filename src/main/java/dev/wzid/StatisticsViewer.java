package dev.wzid;

import dev.wzid.bot.DiscordBot;
import dev.wzid.bot.InvalidConfigException;
import dev.wzid.bot.stats.EmbedStat;
import dev.wzid.bot.MissingTokenException;
import dev.wzid.util.ConfigUtil;
import net.dv8tion.jda.api.JDA;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class StatisticsViewer extends JavaPlugin implements Listener {

    public static StatisticsViewer INSTANCE;
    private JDA jda;
    private final List<EmbedStat> statistics = new ArrayList<>();


    @Override
    public void onEnable() {
        INSTANCE = this;
        ConfigUtil.setDefaultPath(this.getDataFolder().getAbsolutePath());
        DiscordBot bot;

        try {
            bot = new DiscordBot();
            bot.initializeBot(this);
        } catch (InvalidConfigException | InterruptedException e) {
            getLogger().throwing("Discord Bot", "Initialization", e);
            //Dont throw an error to users if they are just starting up the bot it makes no sense
        } catch (MissingTokenException e) {
            getLogger().info(e.getMessage());
        }
    }

    public void setJDA(JDA jda) {
        this.jda = jda;
    }

    public JDA getJDA() {
        return jda;
    }

    public List<EmbedStat> getStatistics() {
        return statistics;
    }
}
