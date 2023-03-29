package com.person98.betterquizsforevents;

import com.person98.betterquizsforevents.commands.*;
import com.person98.betterquizsforevents.listeners.DeathBanListener;
import com.person98.betterquizsforevents.listeners.QuizListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BetterQuizzesForEvents extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save the default config.yml if it doesn't exist
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        // Register commands
        getCommand("deathban").setExecutor(new DeathBanCommand(this));
        getCommand("setdeathbanmessage").setExecutor(new SetDeathBanMessageCommand(this));
        this.getCommand("addquestion").setExecutor(new AddQuestionCommand(this));
        this.getCommand("question").setExecutor(new QuestionCommand(this));
        getCommand("togglespectatorondeath").setExecutor(new ToggleSpectatorOnDeathCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new DeathBanListener(this), this);
        getServer().getPluginManager().registerEvents(new QuizListener(), this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
}
