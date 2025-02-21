
import org.bukkit.scheduler.BukkitTask;

public interface PlayingTask {

    IArena getArena();

    BukkitTask getBukkitTask();

    /**
     * Get bukkit task id.
     */
    int getTask();

    int getBedsDestroyCountdown();

    int getDragonSpawnCountdown();

    int getGameEndCountdown();

    void cancel();
}
