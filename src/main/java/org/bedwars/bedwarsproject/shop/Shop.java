package org.bedwars.bedwarsproject;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final Map<String, Integer> prices = new HashMap<>();

    public Shop() {
        prices.put("Sword", 10);
        prices.put("Armor", 20);
        prices.put("Bow", 15);
    }

    public void openShop(Player player) {
        // Logic to display items and prices to the player
        System.out.println("Shop opened for " + player.getName());
        prices.forEach((item, price) -> {
            System.out.println(item + ": " + price + " gold");
        });
    }

    public boolean purchaseItem(Player player, String item) {
        int price = prices.getOrDefault(item, -1);
        if (price == -1) {
            player.sendMessage("Item not found.");
            return false;
        }
        // Logic to check if the player has enough currency
        // Deduct currency and give item to player
        return true;
    }
}
