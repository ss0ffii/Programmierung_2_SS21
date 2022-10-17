package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;

/** Background of the game */
public class Background extends GameObject {

    /**
     * Defines the style of the background.
     */
    public enum Style {DARK, LIGHT}

    private Style style;

    /**
     * Creates the GameObject with the GameView to be displayed on.
     *
     * @param gameView Window to show the GameObject on.
     */
    public Background(GameView gameView) {
        super(gameView);
        width = GameView.WIDTH + 2 * 420;
        size = 15;
        resetPosition();
    }

    /**
     * Sets the current Style of the level.
     *
     * @param style New Style.
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Resets the position;
     */
    public void resetPosition() {
        this.position = new Position(-420, 0);
    }

    @Override
    public void updateStatus() {
        // No animations.
    }

    @Override
    public void addToCanvas() {
        addBackgroundColor();
    }

    private void addBackgroundColor(){
        if (style == Style.DARK){
            gameView.addRectangleToCanvas(0,0,960, 540, 0, true, Color.GRAY);
        } else if (style == Style.LIGHT){
            gameView.addRectangleToCanvas(0,0,960, 540, 0, true, Color.ORANGE.brighter());
        }
    }
}