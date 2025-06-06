// bedwars/src/main/java/org/bedwars/bedwarsproject/shop/ShopManager.java
package org.bedwars.bedwarsproject.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import java.util.Map;

public class ShopManager {
    private ShopTier currentShopTier;
    private Map<Material, Integer> basePrices; // Store original prices

    public ShopManager() {
        this.basePrices = new HashMap<>();
        // Initialize base prices for all items
        basePrices.put(Material.IRON_SWORD, 10);
        basePrices.put(Material.GOLD_INGOT, 5);
        basePrices.put(Material.DIAMOND_SWORD, 50);
        basePrices.put(Material.ENDER_PEARL, 20);
        basePrices.put(Material.FIRE_CHARGE, 10);
        basePrices.put(Material.IRON_INGOT, 1);
        // ... add all other shop items and their base prices

        this.currentShopTier = ShopTier.EARLY_GAME; // Set initial tier
    }

    public void updateShopTier(ShopTier newTier) {
        if (this.currentShopTier == newTier) return; // No change needed

        this.currentShopTier = newTier;
        // Notify players or update open shop GUIs
        // Bukkit.broadcastMessage("The shop has entered " + newTier.getDisplayName() + " phase!");
        // You might need to refresh all open shop GUIs for players
    }

    public int getItemPrice(Material itemMaterial) {
        int price = basePrices.getOrDefault(itemMaterial, 0); // Get base price

        // Apply price adjustments from current tier
        if (currentShopTier.getPriceAdjustments().containsKey(itemMaterial)) {
            price += currentShopTier.getPriceAdjustments().get(itemMaterial);
        }
        return Math.max(0, price); // Ensure price doesn't go below zero
    }

    public boolean isItemAvailable(Material itemMaterial) {
        // Default to available if not specified in tier
        return currentShopTier.getItemAvailability().getOrDefault(itemMaterial, true);
    }

    // Method to create a shop item for display in GUI
    public ItemStack getShopDisplayItem(Material itemMaterial) {
        if (!isItemAvailable(itemMaterial)) {
            return null; // Item not available in current tier
        }
        ItemStack item = new ItemStack(itemMaterial);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§r§f" + itemMaterial.name().replace("_", " ").toLowerCase());
            meta.setLore(java.util.Arrays.asList("§7Cost: " + getItemPrice(itemMaterial) + " Iron")); // Example cost display
            item.setItemMeta(meta);
        }
        return item;
    }

    // ... existing methods for opening shop, handling purchases, etc.
    // These methods would now use getItemPrice() and isItemAvailable()
}