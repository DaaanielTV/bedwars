// bedwars/src/main/java/org/bedwars/bedwarsproject/commands/AbilityCommand.java
package org.bedwars.bedwarsproject.commands;

import org.bedwars.bedwarsproject.game.GameManager; // Assuming GameManager is accessible
import org.bedwars.bedwarsproject.game.team.Team; // Assuming Team is accessible
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityCommand implements CommandExecutor {

    private final GameManager gameManager; // Inject GameManager instance

    public AbilityCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        // Get the player's team (this logic would depend on your existing team management)
        Team playerTeam = gameManager.getTeamOfPlayer(player.getUniqueId()); // Assuming such a method exists

        if (playerTeam == null) {
            player.sendMessage("You are not on a team.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("activate")) {
            gameManager.activateTeamAbility(player, playerTeam);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("cooldown")) {
            if (playerTeam.getTeamAbility() == null) {
                player.sendMessage("Your team does not have an assigned ability.");
            } else if (playerTeam.isAbilityOnCooldown()) {
                player.sendMessage("Your team ability is on cooldown! Remaining: " + playerTeam.getRemainingCooldown() + "s");
            } else {
                player.sendMessage("Your team ability is ready!");
            }
            return true;
        }

        player.sendMessage("Usage: /ability [activate|cooldown]");
        return true;
    }
}