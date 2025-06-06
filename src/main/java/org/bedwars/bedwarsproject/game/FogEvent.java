// bedwars/src/main/java/org/bedwars/bedwarsproject/game/events/FogEvent.java
package org.bedwars.bedwarsproject.game.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FogEvent implements GameEvent {
    private final String name = "Thick Fog";
    private final String description = "Visibility is greatly reduced!";
    private final int durationTicks = 45 * 20; // 45 seconds
    private boolean running = false;
    private BukkitRunnable fogTask;

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public int getDurationTicks() { return durationTicks; }

    @Override
    public boolean isRunning() { return running; }

    @Override
    public void start(Plugin plugin) {
        running = true;
        Bukkit.broadcastMessage("§7§lEVENT! §r§fA §8Thick Fog§f rolls in, reducing visibility!");

        // Apply blindness effect to all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 0, false, false));
        }

        fogTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!running) {
                    end(plugin);
                    return;
                }
                // Re-apply effect in case players join late or effect wears off prematurely
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 0, false, false));
                    }
                }
            }
        };
        fogTask.runTaskTimer(plugin, 0L, 20L); // Run every 1 second to ensure effect
        // Schedule end of event
        Bukkit.getScheduler().runTaskLater(plugin, () -> end(plugin), durationTicks);
    }

    @Override
    public void end(Plugin plugin) {
        if (fogTask != null) {
            fogTask.cancel();
        }
        running = false;
        Bukkit.broadcastMessage("§a§lEVENT END! §r§fThe §8Thick Fog§f has cleared.");
        // Remove blindness effect (it should wear off, but good to be explicit)
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}