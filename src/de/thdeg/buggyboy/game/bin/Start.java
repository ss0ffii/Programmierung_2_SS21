package de.thdeg.buggyboy.game.bin;

import de.thdeg.buggyboy.game.managers.GameLoopManager;

/**
 * This class starts the game.
 * {@code Start}
 * @author Sofia Gutoranska
 */
public class Start {

    public static void main(String[] args) {
        GameLoopManager gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();
    }
}