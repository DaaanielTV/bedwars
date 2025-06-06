// bedwars/src/main/java/org/bedwars/bedwarsproject/game/team/TeamAbility.java
package org.bedwars.bedwarsproject.game.team;

public enum TeamAbility {
    RAID_RUSH("Raid Rush", 10 * 20, 5 * 60 * 20), // Duration in ticks, Cooldown in ticks
    BED_SHIELD("Bed Shield", 15 * 20, 6 * 60 * 20),
    RESOURCE_SURGE("Resource Surge", 30 * 20, 7 * 60 * 20);

    private final String name;
    private final int durationTicks;
    private final int cooldownTicks;

    TeamAbility(String name, int durationTicks, int cooldownTicks) {
        this.name = name;
        this.durationTicks = durationTicks;
        this.cooldownTicks = cooldownTicks;
    }

    public String getName() { return name; }
    public int getDurationTicks() { return durationTicks; }
    public int getCooldownTicks() { return cooldownTicks; }
}