
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerReJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private IArena arena;
    private int respawnTime;
    private boolean cancelled = false;

    /**
     * Called when a player re-joins the arena.
     * PlayerJoinArenaEvent won't be called
     */
    public PlayerReJoinEvent(Player player, IArena arena, int respawnTime) {
        this.player = player;
        this.arena = arena;
        this.respawnTime = respawnTime;
    }

    /**
     * Get arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * Get player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the amount of time until the player respawns
     * @param respawnTime time in seconds
     */
    public void setRespawnTime(int respawnTime) {
        this.respawnTime = respawnTime;
    }

    /**
     * Gets the amount of time until the player respawns
     * @return time in seconds
     */
    public int getRespawnTime() {
        return respawnTime;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
