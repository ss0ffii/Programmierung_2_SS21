package de.thdeg.buggyboy.graphics.screens;

import de.thdeg.buggyboy.gameview.GameView;

/**
 * Displays the end screen.
 */
public class EndScreen {

    private GameView gameView;

    /**
     * Creates the end screen.
     * @param gameView GameView to show the screen on.
     */
    public EndScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Shows the end screen.
     */
    public void showEndScreen(int score) {
        gameView.showEndScreen("You died!!!\n" + "Your score is " + score);
    }
}