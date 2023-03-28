package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetDeathBanMessageCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public SetDeathBanMessageCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Please provide a death ban message.");
            return false;
        }

        String deathBanMessage = String.join(" ", args);
        plugin.getConfig().set("deathBan.message", deathBanMessage);
        plugin.saveConfig();

        sender.sendMessage("Death ban message has been set to: " + deathBanMessage);
        return true;
    }
}
