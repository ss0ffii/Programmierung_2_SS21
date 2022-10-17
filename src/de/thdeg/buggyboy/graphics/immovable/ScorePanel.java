package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;

/**
 * Displays the current score of the player.
 */
public class ScorePanel extends GameObject {
    private String scoreString;

    /**
     * Crates a new panel to show the current score of the player.
     *
     * @param gameView GameView to show the score on.
     */
    public ScorePanel(GameView gameView) {
        super(gameView);
        this.size = 25;
        this.position = new Position(GameView.WIDTH - 4 * size, 1);
        this.scoreString = " ";
    }

    @Override
    public void updateStatus(){

    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.x, position.y, 3.7 * size, 1.2 * size, 0, true, Color.BLACK);
        gameView.addRectangleToCanvas(position.x, position.y, 3.7 * size, 1.2 * size, 2, false, Color.WHITE);
        gameView.addTextToCanvas(scoreString, position.x, position.y, 1.2 * size, Color.WHITE, rotation);
    }

    /**
     * Sets the current score.
     *
     * @param score The current score.
     */
    public void setScore(int score) {
        scoreString = String.valueOf(score);
        scoreString = "".repeat(6 - scoreString.length()) + score;
    }
}