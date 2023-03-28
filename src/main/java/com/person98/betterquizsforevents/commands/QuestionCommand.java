package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;

public class QuestionCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public QuestionCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Validate the arguments and check for the correct syntax
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /question <question-number>");
            return false;
        }

        // Parse the arguments
        int questionNumber;
        try {
            questionNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid question number. Please enter a valid integer.");
            return false;
        }

        // Retrieve the question, choices, and colors from the configuration using the question number
        String question = plugin.getConfig().getString("questions." + questionNumber + ".question");
        String choice1 = plugin.getConfig().getString("questions." + questionNumber + ".choice1");
        String choice2 = plugin.getConfig().getString("questions." + questionNumber + ".choice2");
        String choice3 = plugin.getConfig().getString("questions." + questionNumber + ".choice3");
        String choice4 = plugin.getConfig().getString("questions." + questionNumber + ".choice4");
        String choice1Color = plugin.getConfig().getString("questions." + questionNumber + ".choice1color");
        String choice2Color = plugin.getConfig().getString("questions." + questionNumber + ".choice2color");
        String choice3Color = plugin.getConfig().getString("questions." + questionNumber + ".choice3color");
        String choice4Color = plugin.getConfig().getString("questions." + questionNumber + ".choice4color");

        if (question == null || choice1 == null || choice2 == null || choice3 == null || choice4 == null || choice1Color == null || choice2Color == null || choice3Color == null || choice4Color == null) {
            sender.sendMessage(ChatColor.RED + "Question not found. Please ensure the question number is correct.");
            return false;
        }

        // Display the question as a title on the screen for all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(ChatColor.GOLD + question, "", 10, 70, 20);
        }

        // Display the choices in chat with different colors
        TextComponent choice1Text = new TextComponent(ChatColor.translateAlternateColorCodes('&', choice1Color + "Choice 1: " + choice1));
        TextComponent choice2Text = new TextComponent(ChatColor.translateAlternateColorCodes('&', choice2Color + "Choice 2: " + choice2));
        TextComponent choice3Text = new TextComponent(ChatColor.translateAlternateColorCodes('&', choice3Color + "Choice 3: " + choice3));
        TextComponent choice4Text = new TextComponent(ChatColor.translateAlternateColorCodes('&', choice4Color + "Choice 4: " + choice4));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(choice1Text);
            player.spigot().sendMessage(choice2Text);
            player.spigot().sendMessage(choice3Text);
            player.spigot().sendMessage(choice4Text);
        }

        return true;
    }
}
