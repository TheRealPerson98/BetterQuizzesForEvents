package com.person98.betterquizsforevents.commands;

import com.person98.betterquizsforevents.BetterQuizzesForEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddQuestionCommand implements CommandExecutor {
    private final BetterQuizzesForEvents plugin;

    public AddQuestionCommand(BetterQuizzesForEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("betterquizsforevents.addquestion")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        // Combine the arguments into a single string
        String input = String.join(" ", args);

        // Use a regex pattern to match questions and choices wrapped in quotes
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(input);

        List<String> extracted = new ArrayList<>();
        while (matcher.find()) {
            extracted.add(matcher.group(1));
        }

        // Validate the extracted arguments
        if (extracted.size() != 5) {
            sender.sendMessage(ChatColor.RED + "Usage: /addquestion \"<question>\" \"<choice1>\" \"<choice2>\" \"<choice3>\" \"<choice4>\" <question-number> <choice1color> <choice2color> <choice3color> <choice4color> <correct-answer>");
            return false;
        }

        String question = extracted.get(0);
        String choice1 = extracted.get(1);
        String choice2 = extracted.get(2);
        String choice3 = extracted.get(3);
        String choice4 = extracted.get(4);

        // Remove the quoted arguments from the args array
        String[] updatedArgs = input.replaceAll("\"(.*?)\"", "").trim().split(" ");

        int questionNumber;

        try {
            questionNumber = Integer.parseInt(updatedArgs[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid question number. Please enter a valid integer.");
            return false;
        }

        // Convert color names to color codes
        try {
            String choice1ColorCode = ChatColor.valueOf(updatedArgs[1].toUpperCase()).toString();
            String choice2ColorCode = ChatColor.valueOf(updatedArgs[2].toUpperCase()).toString();
            String choice3ColorCode = ChatColor.valueOf(updatedArgs[3].toUpperCase()).toString();
            String choice4ColorCode = ChatColor.valueOf(updatedArgs[4].toUpperCase()).toString();
            int correctAnswer;
            try {
                correctAnswer = Integer.parseInt(updatedArgs[5]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid correct answer number. Please enter a valid integer.");
                return false;
            }

            if (correctAnswer < 1 || correctAnswer > 4) {
                sender.sendMessage(ChatColor.RED + "Invalid correct answer number. Please enter a number between 1 and 4.");
                return false;
            }

            // Store the question, choices, question number, and choice colors in the configuration
            plugin.getConfig().set("questions." + questionNumber + ".question", question);
            plugin.getConfig().set("questions." + questionNumber + ".choice1", choice1);
            plugin.getConfig().set("questions." + questionNumber + ".choice2", choice2);
            plugin.getConfig().set("questions." + questionNumber + ".choice3", choice3);
            plugin.getConfig().set("questions." + questionNumber + ".choice4", choice4);
            plugin.getConfig().set("questions." + questionNumber + ".choice1color", updatedArgs[1].toUpperCase());
            plugin.getConfig().set("questions." + questionNumber + ".choice2color", updatedArgs[2].toUpperCase());
            plugin.getConfig().set("questions." + questionNumber + ".choice3color", updatedArgs[3].toUpperCase());
            plugin.getConfig().set("questions." + questionNumber + ".choice4color", updatedArgs[4].toUpperCase());
            plugin.getConfig().set("questions." + questionNumber + ".correctAnswer", correctAnswer);

            // Save the changes to the configuration file
            plugin.saveConfig();

            // Send a success message to the user
            sender.sendMessage(ChatColor.GREEN + "Question added successfully.");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Invalid color name. Please use a valid ChatColor name.");
        }

        return true;
    }
}
