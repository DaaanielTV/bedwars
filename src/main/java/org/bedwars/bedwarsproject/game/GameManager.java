// bedwars/src/main/java/org/bedwars/bedwarsproject/game/GameManager.java
package org.bedwars.bedwarsproject.game;

import org.bedwars.bedwarsproject.game.events.EventManager; // New dependency
import org.bukkit.World;
import org.bukkit.plugin.Plugin; // Your main plugin class

public class GameManager {
    private EventManager eventManager; // New dependency
    private final Plugin plugin; // Reference to your main plugin instance
    private final World gameWorld; // The world where the game is played

    public GameManager(Plugin plugin, World gameWorld) { // Pass plugin and world to constructor
        this.plugin = plugin;
        this.gameWorld = gameWorld;
        // ... other initializations
    }

    // Call this when the game starts
    public void onGameStart() {
        // ... existing game start logic ...
        this.eventManager = new EventManager(plugin, gameWorld);
        this.eventManager.startGameEvents();
    }

    // Call this when the game ends
    public void onGameEnd() {
        // ... existing game end logic ...
        if (this.eventManager != null) {
            this.eventManager.stopGameEvents();
        }
    }

    // ... other existing game management methods
}