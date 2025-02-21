
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBaseLeaveEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private ITeam team;
    private Player p;

    /**
     * Called when a player leaves a team base.
     * Called when the distance between the player and a team's bed is greater than islandRadius
     */
    public PlayerBaseLeaveEvent(Player p, ITeam team) {
        this.p = p;
        this.team = team;
    }


    /**
     * Get the team owing the exited base
     */
    public ITeam getTeam() {
        return team;
    }

    /**
     * Get the player that leaved the base
     */
    public Player getPlayer() {
        return p;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
