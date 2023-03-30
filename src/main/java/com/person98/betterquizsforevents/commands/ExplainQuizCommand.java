package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExplainQuizCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public ExplainQuizCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterquizsforevents.explainquiz")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }

        int quizDuration = plugin.getConfig().getInt("quizDuration", 20);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GOLD + "Welcome to the quiz event!");
            player.sendMessage(ChatColor.YELLOW + "Here's how it works:");
            player.sendMessage(ChatColor.GREEN + "1. A question will appear on your screen.");
            player.sendMessage(ChatColor.GREEN + "2. You have " + quizDuration + " seconds to stand on the correct colored block.");
            player.sendMessage(ChatColor.GREEN + "3. If you're on the correct block when time runs out, you'll survive.");
            player.sendMessage(ChatColor.GREEN + "4. If you're on the wrong block, you will die.");
            player.sendMessage(ChatColor.YELLOW + "Good luck and have fun!");
        }

        QuestionCommand.quizActive = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                QuestionCommand.quizActive = false;
            }
        }.runTaskLater(plugin, 5 * 20); // 5 seconds (5 * 20 ticks)

        return true;
    }
}