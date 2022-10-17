package de.thdeg.buggyboy.graphics.screens;

import de.thdeg.buggyboy.gameview.GameView;

/**
 * Displays the start screen.
 */
public class StartScreen {

    private final GameView gameView;
    private boolean isDifficultySetToEasy;

    /**
     * Creates the start screen.
     * @param gameView GameView to show the screen on.
     */
    public StartScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Shows the screen.
     */
    public void showStartScreen() {
        String title = "GAME";
        String description = "                       Good luck! :)";
        isDifficultySetToEasy = gameView.showSimpleStartScreen(title, description);
    }

    /**
     * Returns the choice of the player.
     * @return <code>true</code>, if the player has chosen "Easy", <code>false</code> otherwise.
     */
    public boolean isDifficultySetToEasy() {
        return isDifficultySetToEasy;
    }
}