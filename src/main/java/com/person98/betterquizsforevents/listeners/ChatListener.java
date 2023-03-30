package com.person98.betterquizsforevents.listeners;

import com.person98.betterquizsforevents.commands.QuestionCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (QuestionCommand.quizActive && !QuestionCommand.exemptPlayers.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cChat is disabled during the quiz.");
        }
    }
}