// bedwars/src/main/java/org/bedwars/bedwarsproject/game/generator/GeneratorUpgradeType.java
package org.bedwars.bedwarsproject.game.generator;

import org.bukkit.Material;

public enum GeneratorUpgradeType {
    IRON_SPEED_1("Iron Speed I", Material.IRON_INGOT, 2, 1.5), // Cost (diamonds), Multiplier
    IRON_SPEED_2("Iron Speed II", Material.IRON_INGOT, 4, 2.0),
    GOLD_SPEED_1("Gold Speed I", Material.GOLD_INGOT, 3, 1.5),
    GOLD_SPEED_2("Gold Speed II", Material.GOLD_INGOT, 6, 2.0),
    DIAMOND_SPEED_1("Diamond Speed I", Material.DIAMOND, 5, 1.2),
    DIAMOND_SPEED_2("Diamond Speed II", Material.DIAMOND, 10, 1.5),
    EMERALD_GENERATION("Emerald Generation", Material.EMERALD, 15, 0.5); // Cost, Interval (seconds)

    private final String name;
    private final Material generatedMaterial; // What resource this upgrade affects
    private final int cost; // Cost in diamonds/emeralds (depending on tier)
    private final double value; // Multiplier for speed, or interval for new resource

    GeneratorUpgradeType(String name, Material generatedMaterial, int cost, double value) {
        this.name = name;
        this.generatedMaterial = generatedMaterial;
        this.cost = cost;
        this.value = value;
    }

    public String getName() { return name; }
    public Material getGeneratedMaterial() { return generatedMaterial; }
    public int getCost() { return cost; }
    public double getValue() { return value; } // Can be multiplier or interval