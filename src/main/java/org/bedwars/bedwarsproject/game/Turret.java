// bedwars/src/main/java/org/bedwars/bedwarsproject/game/turret/Turret.java
package org.bedwars.bedwarsproject.game.turret;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bedwars.bedwarsproject.game.team.Team; // Assuming Team class exists

public class Turret {
    private final TurretType type;
    private final Location location; // Location of the turret block
    private final Team ownerTeam;
    private int currentHealth;
    private int ticksUntilNextShot;

    public Turret(TurretType type, Location location, Team ownerTeam) {
        this.type = type;
        this.location = location;
        this.ownerTeam = ownerTeam;
        this.currentHealth = type.getMaxHealth();
        this.ticksUntilNextShot = type.getFireRateTicks();
    }

    public TurretType getType() { return type; }
    public Location getLocation() { return location; }
    public Team getOwnerTeam() { return ownerTeam; }
    public int getCurrentHealth() { return currentHealth; }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            // Turret destroyed logic (e.g., remove block, notify manager)
            destroy();
        }
    }

    public void tick(Player target) {
        if (currentHealth <= 0) return;

        ticksUntilNextShot--;
        if (ticksUntilNextShot <= 0) {
            if (target != null && target.isOnline() && target.getLocation().distance(location) <= type.getRange()) {
                shoot(target);
                ticksUntilNextShot = type.getFireRateTicks();
            } else {
                ticksUntilNextShot = 10; // Short delay if no target, to re-check
            }
        }
    }

    private void shoot(Player target) {
        // Spawn an arrow from the turret's location
        Arrow arrow = location.getWorld().spawnArrow(
                location.clone().add(0.5, 1.5, 0.5), // Slightly above the block
                target.getLocation().toVector().subtract(location.toVector()).normalize(), // Direction
                2.0f, // Speed
                0.5f // Spread
        );
        arrow.setShooter(null); // Turret is not a living entity, so no shooter
        arrow.setDamage(type.getDamage());
        arrow.setCritical(true); // Make it a critical hit for visual effect
        // Potentially add metadata to the arrow to identify it as a turret shot
        // arrow.setMetadata("turret_shot", new FixedMetadataValue(plugin, true));
    }

    public void destroy() {
        // Remove the turret block from the world
        location.getBlock().setType(Material.AIR);
        // Notify TurretManager to remove this turret from its active list
        // TurretManager.removeTurret(this);
        location.getWorld().strikeLightningEffect(location); // Visual effect
    }
}