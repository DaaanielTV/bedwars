
import org.bukkit.scheduler.BukkitTask;

public interface RestartingTask {

    IArena getArena();

    BukkitTask getBukkitTask();

    /**
     * Get bukkit task id.
     */
    int getTask();

    int getRestarting();

    void cancel();
}
