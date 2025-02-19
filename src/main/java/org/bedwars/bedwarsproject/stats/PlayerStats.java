package org.bedwars.bedwarsproject;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {
    private final Map<String, Integer> wins = new HashMap<>();
    private final Map<String, Integer> losses = new HashMap<>();
    private final Map<String, Integer> kills = new HashMap<>();

    public void addWin(String playerName) {
        wins.put(playerName, wins.getOrDefault(playerName, 0) + 1);
    }

    public void addLoss(String playerName) {
        losses.put(playerName, losses.getOrDefault(playerName, 0) + 1);
    }

    public void addKill(String playerName) {
        kills.put(playerName, kills.getOrDefault(playerName, 0) + 1);
    }

    public int getWins(String playerName) {
        return wins.getOrDefault(playerName, 0);
    }

    public int getLosses(String playerName) {
        return losses.getOrDefault(playerName, 0);
    }

    public int getKills(String playerName) {
        return kills.getOrDefault(playerName, 0);
    }
} 