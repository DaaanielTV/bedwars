// bedwars/src/main/java/org/bedwars/bedwarsproject/game/bed/BedReinforcementType.java
package org.bedwars.bedwarsproject.game.bed;

import org.bukkit.Material;

public enum BedReinforcementType {
    OBSIDIAN_LAYER("Obsidian Layer", Material.OBSIDIAN, 10, 5), // Cost (e.g., diamonds), Max layers
    REGENERATING_SHIELD("Regenerating Shield", Material.DIAMOND_BLOCK, 20, 1), // Cost, Max upgrades
    SPIKE_TRAP("Spike Trap", Material.IRON_INGOT, 5, 3); // Cost, Max traps

    private final String name;
    private final Material costItem;
    private final int costAmount;
    private final int maxUpgrades;

    BedReinforcementType(String name, Material costItem, int costAmount, int maxUpgrades) {
        this.name = name;
        this.costItem = costItem;
        this.costAmount = costAmount;
        this.maxUpgrades = maxUpgrades;
    }

    public String getName() { return name; }
    public Material getCostItem() { return costItem; }
    public int getCostAmount() { return costAmount; }
    public int getMaxUpgrades() { return maxUpgrades; }
}