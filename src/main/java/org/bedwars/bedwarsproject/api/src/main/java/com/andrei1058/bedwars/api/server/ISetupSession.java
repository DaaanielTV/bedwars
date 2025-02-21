
import org.bukkit.entity.Player;

public interface ISetupSession {

    /**
     * Get used world name.
     */
    String getWorldName();

    /**
     * Get player doing the setup.
     */
    Player getPlayer();

    /**
     * Get setup type.
     */
    SetupType getSetupType();

    /**
     * Get arena config.
     */
    ConfigManager getConfig();

    /**
     * Teleport player target world.
     */
    void teleportPlayer();

    /**
     * Close setup session.
     */
    void close();
}
