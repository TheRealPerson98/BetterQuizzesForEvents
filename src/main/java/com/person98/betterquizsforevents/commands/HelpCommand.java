package com.person98.betterquizsforevents.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("betterquizsforevents.bqfehelp")) {
            sender.sendMessage(ChatColor.GOLD + "=== BetterQuizzesForEvents Help ===");
            sender.sendMessage(ChatColor.AQUA + "/addquestion: Add a question to the quiz");
            sender.sendMessage(ChatColor.AQUA + "/question: Display a question to all players");
            sender.sendMessage(ChatColor.AQUA + "/deathban: Toggles Death Bans");
            sender.sendMessage(ChatColor.AQUA + "/setdeathbanmessage: Set death ban message");
            sender.sendMessage(ChatColor.AQUA + "/togglespectatorondeath: Toggle between survival and spectator mode on death");
            sender.sendMessage(ChatColor.AQUA + "/exempt: Exempt a player from quizzes");
            sender.sendMessage(ChatColor.AQUA + "Read the plugin's documentation for setup and usage details.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        return true;
    }
}