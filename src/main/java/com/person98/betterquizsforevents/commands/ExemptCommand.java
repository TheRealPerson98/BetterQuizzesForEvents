package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExemptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterquizsforevents.exempt")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /exempt <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return false;
        }

        if (BetterQuizzesForEvents.exemptPlayers.contains(target.getUniqueId())) {
            BetterQuizzesForEvents.exemptPlayers.remove(target.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + target.getName() + " is no longer exempt from quizzes.");
        } else {
            BetterQuizzesForEvents.exemptPlayers.add(target.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + target.getName() + " is now exempt from quizzes.");
        }

        return true;
    }
}