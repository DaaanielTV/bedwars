// bedwars/src/main/java/org/bedwars/bedwarsproject/game/events/MeteorShowerEvent.java
package org.bedwars.bedwarsproject.game.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import java.util.Random;

public class MeteorShowerEvent implements GameEvent {
    private final String name = "Meteor Shower";
    private final String description = "Meteors fall, dropping valuable resources!";
    private final int durationTicks = 30 * 20; // 30 seconds
    private boolean running = false;
    private BukkitRunnable meteorTask;
    private final Random random = new Random();
    private final World gameWorld; // Reference to the game world

    public MeteorShowerEvent(World gameWorld) {
        this.gameWorld = gameWorld;
    }

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
        Bukkit.broadcastMessage("§c§lEVENT! §r§fA §eMeteor Shower§f has begun!");

        meteorTask = new BukkitRunnable() {
            int ticksElapsed = 0;
            @Override
            public void run() {
                if (!running || ticksElapsed >= durationTicks) {
                    end(plugin);
                    return;
                }

                // Spawn a meteor (falling block)
                spawnMeteor();
                ticksElapsed += 10; // Spawn every 0.5 seconds
            }
        };
        meteorTask.runTaskTimer(plugin, 0L, 10L); // Run every 10 ticks (0.5 seconds)
    }

    private void spawnMeteor() {
        // Get random location within game boundaries (assuming a GameManager provides this)
        // For simplicity, let's pick a random location in the world
        double x = random.nextDouble() * 200 - 100; // Example range
        double z = random.nextDouble() * 200 - 100;
        Location spawnLoc = new Location(gameWorld, x, 200, z); // Spawn high above

        FallingBlock meteor = gameWorld.spawnFallingBlock(spawnLoc, Material.COBBLESTONE.createBlockData()); // Visual meteor
        meteor.setDropItem(false); // Don't drop cobblestone
        meteor.setVelocity(new Vector(0, -1, 0)); // Fall downwards

        // Schedule resource drop when it hits the ground
        new BukkitRunnable() {
            @Override
            public void run() {
                if (meteor.isDead() || !meteor.isValid()) {
                    Location impactLoc = meteor.getLocation();
                    impactLoc.getWorld().createExplosion(impactLoc, 2.0f, false, false); // Small explosion
                    // Drop valuable resources
                    impactLoc.getWorld().dropItemNaturally(impactLoc, new ItemStack(Material.DIAMOND, random.nextInt(3) + 1));
                    if (random.nextDouble() < 0.2) { // 20% chance for emerald
                        impactLoc.getWorld().dropItemNaturally(impactLoc, new ItemStack(Material.EMERALD, 1));
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 1L, 1L); // Check every tick
    }

    @Override
    public void end(Plugin plugin) {
        if (meteorTask != null) {
            meteorTask.cancel();
        }
        running = false;
        Bukkit.broadcastMessage("§a§lEVENT END! §r§fThe §eMeteor Shower§f has ended.");
    }
}