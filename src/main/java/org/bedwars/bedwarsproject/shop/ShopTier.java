// bedwars/src/main/java/org/bedwars/bedwarsproject/shop/ShopTier.java
package org.bedwars.bedwarsproject.shop;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;

public enum ShopTier {
    EARLY_GAME(0, "Early Game"), // Initial state
    MID_GAME_BED_DESTROYED(1, "Mid Game (Bed Destroyed)"), // After 1 bed destroyed
    LATE_GAME_TIME(10 * 60, "Late Game (Time)"), // After 10 minutes
    END_GAME_FINAL_TEAMS(2, "End Game (Final Teams)"); // When only 2 teams remain

    private final int triggerValue; // Minutes, beds destroyed, or teams remaining
    private final String displayName;
    private final Map<Material, Integer> priceAdjustments; // Item Material -> Price Change (positive for increase, negative for decrease)
    private final Map<Material, Boolean> itemAvailability; // Item Material -> Is Available

    ShopTier(int triggerValue, String displayName) {
        this.triggerValue = triggerValue;
        this.displayName = displayName;
        this.priceAdjustments = new HashMap<>();
        this.itemAvailability = new HashMap<>();
    }

    public int getTriggerValue() { return triggerValue; }
    public String getDisplayName() { return displayName; }

    public ShopTier addPriceAdjustment(Material item, int change) {
        this.priceAdjustments.put(item, change);
        return this;
    }

    public ShopTier setItemAvailability(Material item, boolean available) {
        this.itemAvailability.put(item, available);
        return this;
    }

    public Map<Material, Integer> getPriceAdjustments() { return priceAdjustments; }
    public Map<Material, Boolean> getItemAvailability() { return itemAvailability; }

    // Define specific adjustments for each tier
    static {
        EARLY_GAME
            .setItemAvailability(Material.DIAMOND_SWORD, false)
            .setItemAvailability(Material.ENDER_PEARL, false);

        MID_GAME_BED_DESTROYED
            .addPriceAdjustment(Material.IRON_SWORD, -5) // Iron sword cheaper
            .addPriceAdjustment(Material.GOLD_INGOT, 2) // Gold more expensive
            .setItemAvailability(Material.DIAMOND_SWORD, true); // Diamond sword unlocks

        LATE_GAME_TIME
            .addPriceAdjustment(Material.IRON_INGOT, -1) // Iron cheaper
            .addPriceAdjustment(Material.ENDER_PEARL, true) // Ender pearl unlocks
            .addPriceAdjustment(Material.FIRE_CHARGE, -2); // Fireball cheaper

        END_GAME_FINAL_TEAMS
            .addPriceAdjustment(Material.DIAMOND_SWORD, -10) // Diamond sword much cheaper
            .addPriceAdjustment(Material.ENDER_PEARL, -5) // Ender pearl cheaper
            .setItemAvailability(Material.BED, false); // Beds no longer purchasable (if that's a thing)
    }
}