package org.bedwars.bedwarsproject;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerStatsCommand implements CommandExecutor {
    private final PlayerStats playerStats;

    public PlayerStatsCommand(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        int wins = playerStats.getWins(playerName);
        int losses = playerStats.getLosses(playerName);
        int kills = playerStats.getKills(playerName);

        player.sendMessage("Your Stats:");
        player.sendMessage("Wins: " + wins);
        player.sendMessage("Losses: " + losses);
        player.sendMessage("Kills: " + kills);

        return true;
    }
} 