package org.bedwars.bedwarsproject;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {
    private final GameSetup gameSetup;

    public GameCommands(GameSetup gameSetup) {
        this.gameSetup = gameSetup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        switch (command.getName().toLowerCase()) {
            case "mapcreate":
                if (args.length > 0) {
                    gameSetup.createMap(args[0]);
                    player.sendMessage("Map created: " + args[0]);
                } else {
                    player.sendMessage("Please provide a map name.");
                }
                break;
            case "mapsetspawn":
                gameSetup.setSpawnPoint(player);
                player.sendMessage("Spawn point set.");
                break;
            case "mapsetshop":
                gameSetup.setShop(player);
                player.sendMessage("Shop location set.");
                break;
            // Add more commands as needed
            default:
                player.sendMessage("Unknown command.");
                break;
        }
        return true;
    }
}
