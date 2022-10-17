package de.thdeg.buggyboy.game.managers;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.movable.Beetle;

import java.awt.event.KeyEvent;

/**
 * Manages keyboard input of the player. User keystrokes are processed and the plane is controlled.
 */
class InputManager {

    private final GameView gameView;
    private final Beetle beetle;

    InputManager(GameView gameView, Beetle beetle) {
        this.gameView = gameView;
        this.beetle = beetle;
    }

    void updateUserInputs() {
        Integer[] keyCodesOfCurrentlyPressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        for (int keyCode : keyCodesOfCurrentlyPressedKeys){
            if (keyCode == KeyEvent.VK_LEFT) {
                beetle.left();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                beetle.right();
            } else if (keyCode == KeyEvent.VK_UP) {
                beetle.up();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                beetle.down();
            } else if (keyCode == KeyEvent.VK_SPACE) {
                beetle.shoot();
            }
        }
    }
}