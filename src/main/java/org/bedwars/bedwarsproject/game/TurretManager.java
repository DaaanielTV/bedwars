// bedwars/src/main/java/org/bedwars/bedwarsproject/game/turret/TurretManager.java
package org.bedwars.bedwarsproject.game.turret;

import org.bedwars.bedwarsproject.game.GameManager; // Assuming GameManager is accessible
import org.bedwars.bedwarsproject.game.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TurretManager implements Listener {
    private final GameManager gameManager;
    private final List<Turret> activeTurrets;

    public TurretManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.activeTurrets = new ArrayList<>();
    }

    public void addTurret(Turret turret) {
        activeTurrets.add(turret);
    }

    public void removeTurret(Turret turret) {
        activeTurrets.remove(turret);
    }

    // Game loop tick for all turrets
    public void onGameTick() {
        List<Turret> turretsToRemove = new ArrayList<>();
        for (Turret turret : activeTurrets) {
            if (turret.getCurrentHealth() <= 0) {
                turretsToRemove.add(turret);
                continue;
            }

            // Find the closest enemy player within range
            Player target = findClosestEnemy(turret.getLocation(), turret.getType().getRange(), turret.getOwnerTeam());
            turret.tick(target);
        }
        activeTurrets.removeAll(turretsToRemove); // Remove destroyed turrets
    }

    private Player findClosestEnemy(Location loc, double range, Team ownerTeam) {
        Player closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player player : Bukkit.getOnlinePlayers()) {
            // Check if player is in the game, not on owner's team, and within range
            if (gameManager.isPlayerInGame(player) && !gameManager.getTeamOfPlayer(player.getUniqueId()).equals(ownerTeam)) {
                double distance = loc.distance(player.getLocation());
                if (distance <= range && distance < closestDistance) {
                    closest = player;
                    closestDistance = distance;
                }
            }
        }
        return closest;
    }

    // Event handler for placing turrets
    @EventHandler
    public void onPlayerPlaceTurret(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem()) {
            ItemStack item = event.getItem();
            for (TurretType type : TurretType.values()) {
                if (item != null && item.isSimilar(type.getDeployableItem())) {
                    event.setCancelled(true); // Prevent placing the actual item block
                    Player player = event.getPlayer();
                    Team playerTeam = gameManager.getTeamOfPlayer(player.getUniqueId());
                    if (playerTeam == null) {
                        player.sendMessage("You must be on a team to place a turret.");
                        return;
                    }

                    Location placeLoc = event.getClickedBlock().getLocation().add(0, 1, 0); // Place above clicked block
                    // Check if location is valid (e.g., not inside another block)
                    if (placeLoc.getBlock().getType() != Material.AIR) {
                        player.sendMessage("Cannot place turret here.");
                        return;
                    }

                    // Consume item from inventory
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        player.getInventory().removeItem(item);
                    }

                    // Place a visual block for the turret (e.g., a dispenser)
                    placeLoc.getBlock().setType(type.getItemMaterial());

                    // Create and register the new turret
                    Turret newTurret = new Turret(type, placeLoc, playerTeam);
                    addTurret(newTurret);
                    player.sendMessage("You deployed a " + type.getName() + "!");
                    return;
                }
            }
        }
    }

    // Event handler for damaging/breaking turrets
    @EventHandler
    public void onTurretBreak(BlockBreakEvent event) {
        Location brokenLoc = event.getBlock().getLocation();
        Turret brokenTurret = null;
        for (Turret turret : activeTurrets) {
            if (turret.getLocation().equals(brokenLoc)) {
                brokenTurret = turret;
                break;
            }
        }

        if (brokenTurret != null) {
            event.setCancelled(true); // Prevent instant break
            Player breaker = event.getPlayer();
            Team breakerTeam = gameManager.getTeamOfPlayer(breaker.getUniqueId());

            if (breakerTeam != null && breakerTeam.equals(brokenTurret.getOwnerTeam())) {
                // Friendly fire/accidental break - allow if desired, or just cancel
                breaker.sendMessage("You cannot break your own team's turret!");
                return;
            }

            // Damage the turret
            brokenTurret.takeDamage(1); // Or calculate damage based on tool/player
            breaker.sendMessage("You damaged the enemy turret! (" + brokenTurret.getCurrentHealth() + "/" + brokenTurret.getType().getMaxHealth() + ")");

            if (brokenTurret.getCurrentHealth() <= 0) {
                removeTurret(brokenTurret); // Remove from manager
                brokenTurret.destroy(); // Remove block from world
                breaker.sendMessage("You destroyed the enemy turret!");
            }
        }
    }
}