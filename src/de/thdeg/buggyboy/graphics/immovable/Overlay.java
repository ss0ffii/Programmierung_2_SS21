package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;
import java.util.Arrays;

/**
 * Overlay to show messages.
 */
public class Overlay extends GameObject {

    private String message;

    /**
     * Creates an Overlay to show messages.
     *
     * @param gameView Window to show the Overlay on.
     */
    public Overlay(GameView gameView) {
        super(gameView);
        this.size = 36;
    }

    /**
     * Shows a message on the overlay, for 3 seconds. The message can consist of several lines.
     *
     * @param message Message to show.
     */
    public void showMessage(String message) {
        this.message = message;
        String[] lines = message.split(" ");
        int longestLine = Arrays.stream(lines).mapToInt(String::length).max().orElse(1);
        double textHeight = lines.length * size;
        double textWidth = longestLine * size;
        this.position = new Position((GameView.WIDTH - textWidth) / 2d, (GameView.HEIGHT - textHeight) / 2d);
        gameView.setTimer("Show", "Overlay", 3000);
    }

    @Override
    public void updateStatus() {
        // Not needed
    }

    @Override
    public void addToCanvas() {
        if (!gameView.timerExpired("Show", "Overlay")) {
            gameView.addTextToCanvas(message, position.x, position.y, size, Color.BLACK, rotation);
        }
    }
}