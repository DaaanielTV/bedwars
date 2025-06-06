// bedwars/src/main/java/org/bedwars/bedwarsproject/game/events/EventManager.java
package org.bedwars.bedwarsproject.game.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventManager {
    private final Plugin plugin;
    private final World gameWorld; // The world where the game is played
    private final List<GameEvent> availableEvents;
    private GameEvent currentEvent;
    private BukkitRunnable eventSchedulerTask;
    private final Random random = new Random();

    public EventManager(Plugin plugin, World gameWorld) {
        this.plugin = plugin;
        this.gameWorld = gameWorld;
        this.availableEvents = new ArrayList<>();
        // Register available events
        availableEvents.add(new MeteorShowerEvent(gameWorld));
        availableEvents.add(new FogEvent());
        // Add more events here
    }

    public void startGameEvents() {
        // Schedule events to start randomly after a delay
        eventSchedulerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentEvent == null || !currentEvent.isRunning()) {
                    startRandomEvent();
                }
            }
        };
        // Start checking for new events after 5 minutes, then every 2-5 minutes
        eventSchedulerTask.runTaskTimer(plugin, 5 * 60 * 20L, (random.nextInt(3) + 2) * 60 * 20L);
    }

    public void stopGameEvents() {
        if (eventSchedulerTask != null) {
            eventSchedulerTask.cancel();
        }
        if (currentEvent != null && currentEvent.isRunning()) {
            currentEvent.end(plugin);
        }
        currentEvent = null;
    }

    private void startRandomEvent() {
        if (availableEvents.isEmpty()) return;

        GameEvent eventToStart = availableEvents.get(random.nextInt(availableEvents.size()));
        currentEvent = eventToStart;
        currentEvent.start(plugin);
    }

    public GameEvent getCurrentEvent() {
        return currentEvent;
    }
}