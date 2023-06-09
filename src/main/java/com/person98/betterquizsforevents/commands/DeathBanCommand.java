package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeathBanCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public DeathBanCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterquizsforevents.deathban")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        boolean deathBanEnabled = plugin.getConfig().getBoolean("deathBan.enabled");
        plugin.getConfig().set("deathBan.enabled", !deathBanEnabled);
        plugin.saveConfig();

        if (deathBanEnabled) {
            sender.sendMessage("Death ban has been disabled.");
        } else {
            sender.sendMessage("Death ban has been enabled.");
        }

        return true;
    }
}
