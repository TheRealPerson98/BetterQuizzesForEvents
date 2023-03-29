package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleSpectatorOnDeathCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public ToggleSpectatorOnDeathCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a player.");
            return false;
        }

        Player player = (Player) sender;

        if (plugin.getConfig().getBoolean("spectatorOnDeath")) {
            plugin.getConfig().set("spectatorOnDeath", false);
            player.sendMessage("Spectator on death has been disabled.");
        } else {
            plugin.getConfig().set("spectatorOnDeath", true);
            player.sendMessage("Spectator on death has been enabled.");
        }

        plugin.saveConfig();
        return true;
    }
}