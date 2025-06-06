// bedwars/src/main/java/org/bedwars/bedwarsproject/game/team/Team.java
package org.bedwars.bedwarsproject.game.team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.UUID;

public class Team {
    private final ChatColor color;
    private final String name;
    private final List<UUID> players; // List of player UUIDs
    private TeamAbility teamAbility;
    private long lastAbilityActivationTime; // System.currentTimeMillis() or game ticks

    public Team(ChatColor color, String name, List<UUID> players) {
        this.color = color;
        this.name = name;
        this.players = players;
        this.lastAbilityActivationTime = 0; // Initialize to 0
    }

    public void setTeamAbility(TeamAbility ability) {
        this.teamAbility = ability;
    }

    public TeamAbility getTeamAbility() {
        return teamAbility;
    }

    public long getLastAbilityActivationTime() {
        return lastAbilityActivationTime;
    }

    public void setLastAbilityActivationTime(long time) {
        this.lastAbilityActivationTime = time;
    }

    public boolean isAbilityOnCooldown() {
        if (teamAbility == null) return true; // No ability assigned
        long timeSinceLastActivation = System.currentTimeMillis() - lastAbilityActivationTime;
        return timeSinceLastActivation < (teamAbility.getCooldownTicks() / 20 * 1000L); // Convert ticks to milliseconds
    }

    public long getRemainingCooldown() {
        if (teamAbility == null) return 0;
        long elapsed = System.currentTimeMillis() - lastAbilityActivationTime;
        long cooldownMillis = (teamAbility.getCooldownTicks() / 20 * 1000L);
        return Math.max(0, (cooldownMillis - elapsed) / 1000); // Remaining seconds
    }

    // Other existing methods like addPlayer, removePlayer, getBedLocation, etc.
}