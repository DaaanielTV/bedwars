package org.bedwars.bedwarsproject;

import org.bukkit.plugin.java.JavaPlugin;
import org.bedwars.bedwarsproject.GameManager;

public final class BedwarsProject extends JavaPlugin {
    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        gameManager = new GameManager();
        gameManager.startGame();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
