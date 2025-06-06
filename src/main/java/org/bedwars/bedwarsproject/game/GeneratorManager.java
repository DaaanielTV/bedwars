// bedwars/src/main/java/org/bedwars/bedwarsproject/game/generator/GeneratorManager.java
package org.bedwars.bedwarsproject.game.generator;

import org.bedwars.bedwarsproject.game.GameManager; // Assuming GameManager is accessible
import org.bedwars.bedwarsproject.game.team.Team;
import org.bukkit.Location;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GeneratorManager {
    private final GameManager gameManager;
    private final List<ResourceGenerator> generators;
    private final Map<Team, Map<Material, ResourceGenerator>> teamGenerators; // Team -> Material -> Generator

    public GeneratorManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.generators = new ArrayList<>();
        this.teamGenerators = new HashMap<>();
    }

    public void addGenerator(ResourceGenerator generator) {
        generators.add(generator);
        // Also map to team if it's a team-specific generator
        // For example, if generators are tied to team bases:
        // Team team = gameManager.getTeamAtLocation(generator.getLocation()); // Need method to get team by location
        // if (team != null) {
        //     teamGenerators.computeIfAbsent(team, k -> new HashMap<>()).put(generator.getBaseMaterial(), generator);
        // }
    }

    public ResourceGenerator getTeamGenerator(Team team, Material material) {
        return teamGenerators.getOrDefault(team, new HashMap<>()).get(material);
    }

    public void onGameTick() {
        for (ResourceGenerator generator : generators) {
            generator.tick();
        }
    }

    // Method to apply an upgrade to a specific team's generator
    public boolean upgradeTeamGenerator(Team team, GeneratorUpgradeType upgradeType) {
        ResourceGenerator generator = getTeamGenerator(team, upgradeType.getGeneratedMaterial());
        if (generator == null) {
            return false; // Generator not found for this team/material
        }

        // Check if max level reached (if applicable for this upgrade type)
        // This logic would need to be more robust for multi-level upgrades
        // For simplicity, assume each upgrade type is a distinct purchase
        if (generator.getUpgradeLevel(upgradeType) > 0 && upgradeType != GeneratorUpgradeType.EMERALD_GENERATION) {
             // If it's a speed upgrade, you might only allow one level of each
             // Or define max levels per upgrade type in the enum
             return false; // Already upgraded
        }


        generator.applyUpgrade(upgradeType);
        return true;
    }
}