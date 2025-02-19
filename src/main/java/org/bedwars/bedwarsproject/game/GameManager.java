package org.bedwars.bedwarsproject;

public class GameManager {

    private final GameLogic gameLogic;

    public GameManager() {
        this.gameLogic = new GameLogic();
    }

    public void startGame() {
        // Logic to start a new Bedwars game
        System.out.println("Game started!");
        gameLogic.manageGameplay();
    }

    public void stopGame() {
        // Logic to stop the current Bedwars game
        System.out.println("Game stopped!");
    }

    public void manageGameState() {
        // Logic to manage the ongoing game state
        System.out.println("Managing game state...");
    }
}
