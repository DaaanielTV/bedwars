package org.bedwars.bedwarsproject;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;

public class GameSetup {

    public void initializeGame() {
        // Logic to initialize the game environment, teams, and players
        System.out.println("Game setup initialized.");
    }

    public void createMap(String name) {
        // Logic to create a map with the given name
        System.out.println("Map created: " + name);
    }

    public void setSpawnPoint(Player player) {
        Block targetBlock = player.getTargetBlock(null, 5);
        if (targetBlock != null) {
            int x = targetBlock.getX();
            int y = targetBlock.getY();
            int z = targetBlock.getZ();
            setSpawnPoint(x, y, z);
            System.out.println("Spawn point set at: " + x + ", " + y + ", " + z);
        } else {
            System.out.println("No target block found.");
        }
    }

    public void setSpawnPoint(int x, int y, int z) {
        // Logic to set the spawn point at the given coordinates
        System.out.println("Spawn point set at: " + x + ", " + y + ", " + z);
    }

    public void setDiamondSpawn(int x, int y, int z) {
        // Logic to set the diamond spawn point at the given coordinates
        System.out.println("Diamond spawn set at: " + x + ", " + y + ", " + z);
    }

    public void setGoldStore(int x, int y, int z) {
        // Logic to set the gold store point at the given coordinates
        System.out.println("Gold store set at: " + x + ", " + y + ", " + z);
    }

    public void setShop(Player player) {
        // Use the player's target block to set the shop location
        Block targetBlock = player.getTargetBlock(null, 5); // 5 is the max distance to check
        if (targetBlock != null) {
            int x = targetBlock.getX();
            int y = targetBlock.getY();
            int z = targetBlock.getZ();
            System.out.println("Shop set at: " + x + ", " + y + ", " + z);
        } else {
            System.out.println("No target block found.");
        }
    }

    public void setBedBlock(int x, int y, int z) {
        // Logic to set the bed block location at the given coordinates
        System.out.println("Bed block set at: " + x + ", " + y + ", " + z);
    }
}
