package com.person98.betterquizsforevents.listeners;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import com.person98.betterquizsforevents.commands.QuestionCommand;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class QuizListener implements Listener {
    private static HashSet<Player> correctPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Block block = location.getWorld().getBlockAt(location.subtract(0, 1, 0));
        ChatColor blockColor = getColorFromBlock(block.getType());

        if (blockColor != null && blockColor.equals(QuestionCommand.correctAnswerColor)) {
            correctPlayers.add(player);
        } else {
            correctPlayers.remove(player);
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        BetterQuizzesForEvents plugin = JavaPlugin.getPlugin(BetterQuizzesForEvents.class);
        if (plugin.getConfig().getBoolean("spectatorOnDeath")) {
            Player player = event.getEntity();
            Bukkit.getScheduler().runTask(plugin, () -> player.setGameMode(GameMode.SPECTATOR));
        }
    }
    public static void endQuestion() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!BetterQuizzesForEvents.exemptPlayers.contains(player.getUniqueId())) {
                Location location = player.getLocation();
                Block block = location.getWorld().getBlockAt(location.subtract(0, 1, 0));
                ChatColor blockColor = getColorFromBlock(block.getType());

                if (blockColor == null || !blockColor.equals(QuestionCommand.correctAnswerColor)) {
                    player.setHealth(0); // Kill the player
                }
            }
        }
        correctPlayers.clear(); // Clear the HashSet
    }

    private static ChatColor getColorFromBlock(Material blockType) {
        // Determine the color based on the block type and return the corresponding ChatColor
        // Updated to include all concrete and wool colors
        switch (blockType) {
            case RED_CONCRETE:
            case RED_WOOL:
                return ChatColor.RED;
            case BLUE_CONCRETE:
            case BLUE_WOOL:
                return ChatColor.BLUE;
            case GREEN_CONCRETE:
            case GREEN_WOOL:
                return ChatColor.GREEN;
            case YELLOW_CONCRETE:
            case YELLOW_WOOL:
                return ChatColor.YELLOW;
            case LIGHT_BLUE_CONCRETE:
            case LIGHT_BLUE_WOOL:
                return ChatColor.AQUA;
            case BLACK_CONCRETE:
            case BLACK_WOOL:
                return ChatColor.BLACK;
            case PURPLE_CONCRETE:
            case PURPLE_WOOL:
                return ChatColor.DARK_PURPLE;
            case GRAY_CONCRETE:
            case GRAY_WOOL:
                return ChatColor.GRAY;
            case WHITE_CONCRETE:
            case WHITE_WOOL:
                return ChatColor.WHITE;
            default:
                return null;
        }
    }
}
