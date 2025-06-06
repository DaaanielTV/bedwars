package org.bedwars.bedwarsproject;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * GameEventListener handles game-related events.
 * Register this listener in your plugin's main class.
 */
public class GameEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // When a player joins, send a welcome message.
        event.getPlayer().sendMessage("Welcome to Bedwars!");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // When a player leaves, log the event.
        System.out.println(event.getPlayer().getName() + " has left the game.");
    }

    // Add more game event handling methods as needed.
}