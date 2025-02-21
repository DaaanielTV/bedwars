
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IArena arena;
    private final Player victim;
    private ITeam victimTeam;
    private final Player killer;
    private ITeam killerTeam;
    private final PlayerKillCause cause;
    private Function<Player, String> message;
    private boolean playSound = true;

    /**
     * Called when a Player got killed during the game.
     *
     * @param killer can be NULL.
     */
    @Deprecated()
    public PlayerKillEvent(
            @NotNull IArena arena,
            Player victim,
            Player killer,
            Function<Player, String> message,
            PlayerKillCause cause) {
        victimTeam = arena.getTeam(victim);
        if (null == victimTeam) {
            victimTeam = arena.getExTeam(victim.getUniqueId());
        }

        if (null != killer) {
            killerTeam = arena.getTeam(killer);
            if (null == killerTeam) {
                killerTeam = arena.getExTeam(killer.getUniqueId());
            }
        }
        this.arena = arena;
        this.victim = victim;
        this.killer = killer;
        this.message = message;
        this.cause = cause;
    }

    public PlayerKillEvent(
            @NotNull IArena arena,
            @NotNull Player victim,
            @Nullable ITeam victimTeam,
            @Nullable Player killer,
            @Nullable ITeam killerTeam,
            @Nullable Function<Player, String> message,
            @NotNull PlayerKillCause cause) {
        this.arena = arena;
        this.victim = victim;
        this.killer = killer;
        this.message = message;
        this.cause = cause;
        this.victimTeam = victimTeam;
        this.killerTeam = killerTeam;
    }

    public enum PlayerKillCause {
        UNKNOWN(false, false, false),
        UNKNOWN_FINAL_KILL(true, false, false),
        EXPLOSION(false, false, false),
        EXPLOSION_FINAL_KILL(true, false, false),
        VOID(false, false, false),
        VOID_FINAL_KILL(true, false, false),
        PVP(false, false, false),
        PVP_FINAL_KILL(true, false, false),
        PLAYER_SHOOT(false, false, false),
        PLAYER_SHOOT_FINAL_KILL(true, false, false),
        SILVERFISH(false, true, false),
        SILVERFISH_FINAL_KILL(true, true, false),
        IRON_GOLEM(false, true, false),
        IRON_GOLEM_FINAL_KILL(true, true, false),
        PLAYER_PUSH(false, false, false),
        /**
         * Corresponds to FALL on ground.
         */
        PLAYER_PUSH_FINAL(true, false, false),
        PLAYER_DISCONNECT(false, false, true),
        PLAYER_DISCONNECT_FINAL(true, false, true);

        private final boolean finalKill;
        private final boolean despawnable;
        private final boolean pvpLogOut;

        PlayerKillCause(boolean finalKill, boolean despawnable, boolean pvpLogOut) {
            this.finalKill = finalKill;
            this.despawnable = despawnable;
            this.pvpLogOut = pvpLogOut;
        }

        public boolean isFinalKill() {
            return finalKill;
        }

        /**
         * @return true if killed by a player's ironGolem, silverfish etc.
         */
        public boolean isDespawnable() {
            return despawnable;
        }

        public boolean isPvpLogOut() {
            return pvpLogOut;
        }
    }

    /**
     * Killer can be NULL (void etc.)
     */
    public Player getKiller() {
        return killer;
    }

    /**
     * Get kill chat message.
     */
    public @Nullable Function<Player, String> getMessage() {
        return message;
    }

    /**
     * Set chat message.
     */
    public void setMessage(@Nullable Function<Player, String> message) {
        this.message = message;
    }

    /**
     * Get kill cause
     */
    public PlayerKillCause getCause() {
        return cause;
    }

    public IArena getArena() {
        return arena;
    }

    /**
     * Get the Player who died.
     */
    public Player getVictim() {
        return victim;
    }

    /**
     * Checks if the killer gets the kill sound
     */
    public boolean playSound() {
        return playSound;
    }

    /**
     * Set if the killer should get the kill sound
     */
    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }


    public ITeam getKillerTeam() {
        return killerTeam;
    }

    public ITeam getVictimTeam() {
        return victimTeam;
    }

    public void setKillerTeam(ITeam killerTeam) {
        this.killerTeam = killerTeam;
    }

    public void setVictimTeam(ITeam victimTeam) {
        this.victimTeam = victimTeam;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
