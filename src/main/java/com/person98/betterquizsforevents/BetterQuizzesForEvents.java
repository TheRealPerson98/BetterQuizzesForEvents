package com.person98.betterquizsforevents;

import com.person98.betterquizsforevents.commands.*;
import com.person98.betterquizsforevents.listeners.DeathBanListener;
import com.person98.betterquizsforevents.listeners.QuizListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BetterQuizzesForEvents extends JavaPlugin {
    public static Set<UUID> exemptPlayers = new HashSet<>();

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
        this.getCommand("exempt").setExecutor(new ExemptCommand());


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
