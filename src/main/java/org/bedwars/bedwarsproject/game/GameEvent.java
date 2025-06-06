// bedwars/src/main/java/org/bedwars/bedwarsproject/game/events/GameEvent.java
package org.bedwars.bedwarsproject.game.events;

import org.bukkit.plugin.Plugin; // Your main plugin class

public interface GameEvent {
    String getName();
    String getDescription();
    int getDurationTicks(); // Duration of the event
    void start(Plugin plugin); // Logic to start the event
    void end(Plugin plugin); // Logic to end the event
    boolean isRunning();
}