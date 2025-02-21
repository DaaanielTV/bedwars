
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public abstract class RestoreAdapter {

    private final Plugin plugin;

    /**
     * @param owner adapter owner.
     */
    public RestoreAdapter(Plugin owner) {
        this.plugin = owner;
    }

    /**
     * Get adapter owner.
     */
    public Plugin getOwner() {
        return plugin;
    }

    /**
     * Load the world.
     * Arenas will be initialized automatically based on WorldLoadEvent.
     */
    public abstract void onEnable(IArena a);

    /**
     * Restore the world.
     * call new Arena when it's done.
     */
    public abstract void onRestart(IArena a);

    /**
     * Unload the world.
     * This is usually used for /bw unloadArena name
     */
    public abstract void onDisable(IArena a);

    /**
     * Load the world for setting it up.
     */
    public abstract void onSetupSessionStart(ISetupSession s);

    /**
     * Unload the world.
     */
    public abstract void onSetupSessionClose(ISetupSession s);

    /**
     * Remove lobby blocks.
     */
    public void onLobbyRemoval(@NotNull IArena a) {
        this.foreachBlockInRegion(
                a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                a.getConfig().getArenaLoc(ConfigPath.ARENA_WAITING_POS2),
                (block) -> block.setType(Material.AIR)
        );

        Bukkit.getScheduler().runTaskLater(getOwner(), () -> clearItems(a.getWorld()), 15L);
    }

    /**
     * Check if given world exists.
     */
    public abstract boolean isWorld(String name);

    /**
     * Delete a world.
     */
    public abstract void deleteWorld(String name);

    /**
     * Clone an arena world.
     */
    public abstract void cloneArena(String name1, String name2);

    /**
     * Get world container.
     */
    public abstract List<String> getWorldsList();

    /**
     * Convert worlds if it is necessary before loading them.
     * Let them load on BedWars1058 main Thread, so they will be converted before getting loaded.
     */
    public abstract void convertWorlds();

    public abstract String getDisplayName();

    public void foreachBlockInRegion(
            @Nullable Location corner1, @Nullable Location corner2,
            @NotNull Consumer<Block> consumer
            ) {
        if (null == corner1 || null == corner2) {
            return;
        }

        Vector min = new Vector(
                Math.min(corner1.getBlockX(), corner2.getBlockX()),
                Math.min(corner1.getBlockY(), corner2.getBlockY()),
                Math.min(corner1.getBlockZ(), corner2.getBlockZ())
        );

        Vector max = new Vector(
                Math.max(corner1.getBlockX(), corner2.getBlockX()),
                Math.max(corner1.getBlockY(), corner2.getBlockY()),
                Math.max(corner1.getBlockZ(), corner2.getBlockZ())
        );

        for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    consumer.accept(corner1.getWorld().getBlockAt(x, y, z));
                }
            }
        }
    }

    public void clearItems(@NotNull World world) {
        world.getEntities().forEach(e -> {
            if (e instanceof Item) e.remove();
        });
    }
}
