package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;

/**
 * Shows the number of lives that are left.
 */
public class LivesPanel extends GameObject {

    private final static String LIVES_IMAGE =
            """
                    P \s
                    PP\s
                    PPP
                    PP\s
                    P \s
                    """
            ;
    private int lives;

    /**
     * Crates a new panel to show the current lives.
     *
     * @param gameView GameView to show the score on.
     */
    public LivesPanel(GameView gameView) {
        super(gameView);
        this.size = 3;
        this.position = new Position(10, 10);
    }

    @Override
    public void updateStatus(){

    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(1, 1, 140, 30, 0, true, Color.BLACK);
        gameView.addRectangleToCanvas(1, 1, 140, 30, 2, false, Color.MAGENTA);
        for (int i = 0; i < lives; i++) {
            gameView.addBlockImageToCanvas(LIVES_IMAGE, position.x + i * (35 * size - 50), position.y, size, rotation);
        }
    }

    /**
     * Sets the current number of lives.
     * @param lives The current number of lives.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
}