package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;

/**
 * Displays a countdown.
 */
public class CountdownPanel extends GameObject {

    private String timeLeftString;

    /**
     * Creates the Countdown panel.
     *
     * @param gameView Window to show the GameObject on.
     */
    public CountdownPanel(GameView gameView) {
        super(gameView);
        this.size = 27;
        this.position = new Position((GameView.WIDTH - 8 * size) / 2d, 0);
    }

    @Override
    public void updateStatus(){

    }

    /**
     * Sets the time that is currently left in the game.
     * @param timeLeft The time that is left
     */
    public void setTimeLeft(int timeLeft){
        timeLeftString = String.valueOf(timeLeft);
        if (timeLeft < 20){
            timeLeftString = "" + timeLeftString;
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.x, position.y, 8 * size, size, 0, true, Color.BLACK);
        gameView.addRectangleToCanvas(position.x, position.y, 8 * size, size, 1, false, Color.WHITE);
        gameView.addTextToCanvas("Time: " + timeLeftString, position.x, position.y, size, Color.WHITE, rotation);
    }
}