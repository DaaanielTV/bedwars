

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Party {

    boolean hasParty(Player p);

    int partySize(Player p);

    boolean isOwner(Player p);

    List<Player> getMembers(Player owner);

    void createParty(Player owner, Player... members);

    void addMember(Player owner, Player member);

    void removeFromParty(Player member);

    void disband(Player owner);

    boolean isMember(Player owner, Player check);

    void removePlayer(Player owner, Player target);

    default Player getOwner(Player member) {
        for (Player m: this.getMembers(member)) {
            if (isOwner(m)) {
                return m;
            }
        }
        return null;
    }

    default void promote(@NotNull Player owner, @NotNull Player target) {
        String msg = ChatColor.RED+"Not implemented! Contact an administrator";
        owner.sendMessage(msg);
        target.sendMessage(msg);
    }

    boolean isInternal();
}
