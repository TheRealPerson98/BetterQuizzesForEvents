package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scheduler.BukkitRunnable;
import com.person98.betterquizsforevents.listeners.QuizListener;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;


public class QuestionCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;
    public static int currentQuestion;
    public static int correctAnswer;
    public static HashSet<Player> exemptPlayers = new HashSet<>();

    public QuestionCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }
    public static ChatColor correctAnswerColor;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterquizsforevents.question")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
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
        ChatColor choice1ChatColor = ChatColor.valueOf(choice1Color.toUpperCase());
        ChatColor choice2ChatColor = ChatColor.valueOf(choice2Color.toUpperCase());
        ChatColor choice3ChatColor = ChatColor.valueOf(choice3Color.toUpperCase());
        ChatColor choice4ChatColor = ChatColor.valueOf(choice4Color.toUpperCase());

        TextComponent choice1Text = new TextComponent(choice1ChatColor + "Choice 1: " + choice1);
        TextComponent choice2Text = new TextComponent(choice2ChatColor + "Choice 2: " + choice2);
        TextComponent choice3Text = new TextComponent(choice3ChatColor + "Choice 3: " + choice3);
        TextComponent choice4Text = new TextComponent(choice4ChatColor + "Choice 4: " + choice4);


        // Display the question as a title on the screen for all non-exempt players
        // and as a chat message for exempt players
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!BetterQuizzesForEvents.exemptPlayers.contains(player.getUniqueId())) {
                player.sendTitle(ChatColor.GOLD + question, "", 10, 70, 20);
            } else {
                player.sendMessage(ChatColor.GOLD + question);
            }
        }

        // Display the choices in chat for all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(choice1Text);
            player.spigot().sendMessage(choice2Text);
            player.spigot().sendMessage(choice3Text);
            player.spigot().sendMessage(choice4Text);
        }
        int correctAnswer = plugin.getConfig().getInt("questions." + questionNumber + ".correctAnswer");
        correctAnswerColor = ChatColor.valueOf(plugin.getConfig().getString("questions." + questionNumber + ".choice" + correctAnswer + "color"));

        int quizDuration = plugin.getConfig().getInt("quizDuration", 20); // Get the quiz duration from the config, default to 20 seconds
        BukkitTask countdownTask = new BukkitRunnable() {
            int remainingSeconds = quizDuration;

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(ChatColor.GREEN + String.format("%d.%02d", remainingSeconds / 20, remainingSeconds % 20 * 5));

                        if (remainingSeconds <= 100) { // Check if there are 5 seconds or less remaining
                            player.sendTitle(ChatColor.RED + String.valueOf(remainingSeconds / 20), "", 0, 20, 0);
                        }

                }

                remainingSeconds--;

                if (remainingSeconds < 0) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);

        // End the question and cancel the countdown timer
        new BukkitRunnable() {
            @Override
            public void run() {
                QuizListener.endQuestion();
                countdownTask.cancel();
            }
        }.runTaskLater(plugin, quizDuration * 20); // Convert quiz duration to ticks (20 ticks per second)

        return true;
    }
}