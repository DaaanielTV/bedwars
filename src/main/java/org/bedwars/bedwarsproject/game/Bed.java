// bedwars/src/main/java/org/bedwars/bedwarsproject/game/bed/Bed.java
package org.bedwars.bedwarsproject.game.bed;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bedwars.bedwarsproject.game.team.Team; // Assuming Team class exists
import java.util.HashMap;
import java.util.Map;

public class Bed {
    private final Team team;
    private final Location bedLocation; // Location of the bed block
    private boolean isDestroyed;
    private Map<BedReinforcementType, Integer> reinforcements; // Type -> Current Level
    private int shieldHealth; // For Regenerating Shield
    private int maxShieldHealth;
    private long lastShieldRegenTime;

    public Bed(Team team, Location bedLocation) {
        this.team = team;
        this.bedLocation = bedLocation;
        this.isDestroyed = false;
        this.reinforcements = new HashMap<>();
        this.shieldHealth = 0;
        this.maxShieldHealth = 0;
        this.lastShieldRegenTime = 0;
    }

    public Team getTeam() { return team; }
    public Location getBedLocation() { return bedLocation; }
    public boolean isDestroyed() { return isDestroyed; }
    public void setDestroyed(boolean destroyed) { isDestroyed = destroyed; }

    public int getReinforcementLevel(BedReinforcementType type) {
        return reinforcements.getOrDefault(type, 0);
    }

    public boolean applyReinforcement(BedReinforcementType type) {
        int currentLevel = getReinforcementLevel(type);
        if (currentLevel >= type.getMaxUpgrades()) {
            return false; // Max upgrades reached
        }

        reinforcements.put(type, currentLevel + 1);

        // Apply visual/mechanical changes based on type
        switch (type) {
            case OBSIDIAN_LAYER:
                // Logic to place obsidian blocks around the bed
                // This would involve iterating around the bedLocation and placing blocks
                placeObsidianAroundBed(bedLocation);
                break;
            case REGENERATING_SHIELD:
                this.maxShieldHealth += 50; // Increase max shield health per upgrade
                this.shieldHealth = this.maxShieldHealth; // Fully heal shield on upgrade
                this.lastShieldRegenTime = System.currentTimeMillis();
                break;
            case SPIKE_TRAP:
                // Logic to activate/place a trap mechanism around the bed
                // This might involve setting a flag or placing a hidden block
                break;
        }
        return true;
    }

    private void placeObsidianAroundBed(Location loc) {
        // Example: Place obsidian in a 3x3 square around the bed, one layer deep
        // This is a simplified example, actual implementation would need to consider bed orientation
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue; // Don't replace the bed itself
                Block block = loc.clone().add(x, 0, z).getBlock();
                if (block.getType() == Material.AIR || block.getType().isOccluding()) { // Avoid replacing solid blocks
                    block.setType(Material.OBSIDIAN);
                }
            }
        }
    }

    public void damageShield(int amount) {
        if (shieldHealth > 0) {
            shieldHealth = Math.max(0, shieldHealth - amount);
        }
    }

    public void tickShieldRegen() {
        if (getReinforcementLevel(BedReinforcementType.REGENERATING_SHIELD) > 0 && shieldHealth < maxShieldHealth) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShieldRegenTime >= 2000) { // Regenerate every 2 seconds
                shieldHealth = Math.min(maxShieldHealth, shieldHealth + 5); // Regenerate 5 health
                lastShieldRegenTime = currentTime;
            }
        }
    }

    public boolean hasActiveShield() {
        return getReinforcementLevel(BedReinforcementType.REGENERATING_SHIELD) > 0 && shieldHealth > 0;
    }

    // Other methods related to bed destruction, etc.
}