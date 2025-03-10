import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bedwars.bedwarsproject.commands.subcommands.SubCommand;
import org.bedwars.bedwarsproject.commands.parentcommands.ParentCommand;

public class CmdStats extends SubCommand {

    public CmdStats(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(16);
        showInList(false);
        setDisplayInfo("§6 ▪ §7/" + parent.getName() + " " + getSubCommandName() + " §fOpens the stats GUI.");
    }

    private static ConcurrentHashMap<UUID, Long> statsCoolDown = new ConcurrentHashMap<>();

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;

        if (statsCoolDown.containsKey(p.getUniqueId())) {
            if (System.currentTimeMillis() - 3000 >= statsCoolDown.get(p.getUniqueId())) {
                statsCoolDown.replace(p.getUniqueId(), System.currentTimeMillis());
            } else {
                // wait 3 seconds
                return true;
            }
        } else {
            statsCoolDown.put(p.getUniqueId(), System.currentTimeMillis());
        }

        openStatsGUI(p);
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return new ArrayList<>();
    }

    @Override
    public boolean canSee(CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        return hasPermission(s);
    }

    public static ConcurrentHashMap<UUID, Long> getStatsCoolDown() {
        return statsCoolDown;
    }

    private void openStatsGUI(Player player) {
        // Implement your GUI opening logic here
    }
}
