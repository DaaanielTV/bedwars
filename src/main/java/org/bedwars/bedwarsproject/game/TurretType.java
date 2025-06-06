// bedwars/src/main/java/org/bedwars/bedwarsproject/game/turret/TurretType.java
package org.bedwars.bedwarsproject.game.turret;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public enum TurretType {
    BASIC_TURRET("Basic Turret", Material.DISPENSER, 10, 20.0, 20, 2 * 20, 5), // Cost (iron), Range, Health, FireRate (ticks), Damage
    ADVANCED_TURRET("Advanced Turret", Material.DROPPER, 20, 25.0, 30, 1 * 20, 8);

    private final String name;
    private final Material itemMaterial; // Material for the deployable item
    private final int cost; // Cost in iron ingots
    private final double range;
    private final int maxHealth;
    private final int fireRateTicks; // Ticks between shots (20 ticks = 1 second)
    private final double damage;

    TurretType(String name, Material itemMaterial, int cost, double range, int maxHealth, int fireRateTicks, double damage) {
        this.name = name;
        this.itemMaterial = itemMaterial;
        this.cost = cost;
        this.range = range;
        this.maxHealth = maxHealth;
        this.fireRateTicks = fireRateTicks;
        this.damage = damage;
    }

    public String getName() { return name; }
    public Material getItemMaterial() { return itemMaterial; }
    public int getCost() { return cost; }
    public double getRange() { return range; }
    public int getMaxHealth() { return maxHealth; }
    public int getFireRateTicks() { return fireRateTicks; }
    public double getDamage() { return damage; }

    public ItemStack getDeployableItem() {
        ItemStack item = new ItemStack(itemMaterial);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§r§a" + name);
            meta.setLore(Arrays.asList(
                    "§7Cost: " + cost + " Iron",
                    "§7Range: " + range + " blocks",
                    "§7Health: " + maxHealth,
                    "§7Damage: " + damage
            ));
            item.setItemMeta(meta);
        }
        return item;
    }
}