package dev.wzid.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class BukkitUtil {

    public static Map<String, Integer> getTopPlayersByStat(Statistic stat) {
        List<Map.Entry<String, Integer>> playerEntries = new ArrayList<>();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            playerEntries.add(new AbstractMap.SimpleEntry<>(offlinePlayer.getName(), offlinePlayer.getStatistic(stat)));
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerEntries.add(new AbstractMap.SimpleEntry<>(onlinePlayer.getName(), onlinePlayer.getStatistic(stat)));
        }

        return playerEntries.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry<String, Integer>::getValue)))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}
