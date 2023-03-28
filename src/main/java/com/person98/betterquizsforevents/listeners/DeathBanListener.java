package com.person98.betterquizsforevents.listeners;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DeathBanListener implements Listener {
    private final BetterQuizzesForEvents plugin;

    public DeathBanListener(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!plugin.getConfig().getBoolean("deathBan.enabled")) {
            plugin.getLogger().info("Death bans are disabled, not banning player.");
            return;
        }

        Player player = event.getEntity();
        BanList banList = plugin.getServer().getBanList(Type.NAME);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime banEndTime = currentTime.plus(5, ChronoUnit.HOURS);
        Date banEndDate = Date.from(banEndTime.atZone(ZoneId.systemDefault()).toInstant());

        String banMessage = plugin.getConfig().getString("deathBan.message", "You have been temporarily banned for dying.");

        BanEntry banEntry = banList.addBan(player.getName(), banMessage, banEndDate, null);
        if (banEntry == null) {
            plugin.getLogger().warning("Failed to add ban entry for player: " + player.getName());
        } else {
            plugin.getLogger().info("Player " + player.getName() + " has been temporarily banned until " + banEndDate);
        }
        player.kickPlayer(banMessage);
    }
}
