

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAfkEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private AFKType afkType;

    /**
     * Called when a Player goes AFK or comes back from AFK.
     * Check AFKType for more.
     */
    public PlayerAfkEvent(Player player, AFKType afkType) {
        this.afkType = afkType;
        this.player = player;
    }

    /**
     * Get player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Check if player target went AFK or came back
     */
    public AFKType getAfkType() {
        return afkType;
    }

    public enum AFKType {
        /**
         * When a player goes AFK
         */
        START,

        /**
         * When a player comes back from AFK
         */
        END
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
