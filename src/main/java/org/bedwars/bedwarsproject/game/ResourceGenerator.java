// bedwars/src/main/java/org/bedwars/bedwarsproject/game/generator/ResourceGenerator.java
package org.bedwars.bedwarsproject.game.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class ResourceGenerator {
    private final Location location;
    private final Material baseMaterial; // e.g., Material.IRON_INGOT
    private int baseIntervalTicks; // e.g., 20 ticks for 1 second
    private long lastGenerationTime;
    private Map<GeneratorUpgradeType, Integer> upgrades; // Type -> Level

    public ResourceGenerator(Location location, Material baseMaterial, int baseIntervalTicks) {
        this.location = location;
        this.baseMaterial = baseMaterial;
        this.baseIntervalTicks = baseIntervalTicks;
        this.lastGenerationTime = System.currentTimeMillis();
        this.upgrades = new HashMap<>();
    }

    public Location getLocation() { return location; }
    public Material getBaseMaterial() { return baseMaterial; }

    public void applyUpgrade(GeneratorUpgradeType type) {
        upgrades.put(type, upgrades.getOrDefault(type, 0) + 1);
        // Update properties based on upgrade
        if (type.getGeneratedMaterial() == baseMaterial) {
            // For speed upgrades, adjust interval
            this.baseIntervalTicks = (int) (this.baseIntervalTicks / type.getValue());
        }
        // For new resource generation, the tick method will handle it
    }

    public int getUpgradeLevel(GeneratorUpgradeType type) {
        return upgrades.getOrDefault(type, 0);
    }

    public void tick() {
        long currentTime = System.currentTimeMillis();

        // Handle base material generation
        if (currentTime - lastGenerationTime >= (baseIntervalTicks / 20 * 1000L)) {
            generateItem(baseMaterial);
            lastGenerationTime = currentTime;
        }

        // Handle Emerald Generation upgrade
        if (getUpgradeLevel(GeneratorUpgradeType.EMERALD_GENERATION) > 0) {
            // Assuming 'value' for EMERALD_GENERATION is the interval in seconds
            long emeraldIntervalMillis = (long) (GeneratorUpgradeType.EMERALD_GENERATION.getValue() * 1000L);
            if (currentTime - lastGenerationTime >= emeraldIntervalMillis) { // This logic needs refinement if baseInterval is also changing
                generateItem(Material.EMERALD);
                // Reset lastGenerationTime for emeralds specifically, or use separate timers
            }
        }
    }

    private void generateItem(Material material) {
        ItemStack item = new ItemStack(material);
        Item droppedItem = location.getWorld().dropItem(location.clone().add(0.5, 1.0, 0.5), item);
        droppedItem.setVelocity(new Vector(0, 0.1, 0)); // Make it float up slightly
    }
}