package com.person98.betterquizsforevents;

import com.person98.betterquizsforevents.commands.AddQuestionCommand;
import com.person98.betterquizsforevents.commands.DeathBanCommand;
import com.person98.betterquizsforevents.commands.QuestionCommand;
import com.person98.betterquizsforevents.commands.SetDeathBanMessageCommand;
import com.person98.betterquizsforevents.listeners.DeathBanListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterQuizzesForEvents extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save the default config.yml if it doesn't exist
        saveDefaultConfig();
        // Register commands
        getCommand("deathban").setExecutor(new DeathBanCommand(this));
        getCommand("setdeathbanmessage").setExecutor(new SetDeathBanMessageCommand(this));
        this.getCommand("addquestion").setExecutor(new AddQuestionCommand(this));
        this.getCommand("question").setExecutor(new QuestionCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new DeathBanListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
