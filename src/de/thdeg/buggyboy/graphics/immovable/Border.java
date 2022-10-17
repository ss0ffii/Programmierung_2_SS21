package de.thdeg.buggyboy.graphics.immovable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.MovingGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;

/**
 * Represents a cactus in the game.
 */
public class Border extends CollidableGameObject implements MovingGameObject {

    private enum Status {STANDARD, MOVED}

    private Status status;

    private final boolean isUpBorder;

    /**
     * Crates a new border.
     *
     * @param gameView   Window to show the GameObject on.
     * @param isUpBorder Determines if this is the up or the down border.
     */
    public Border(GameView gameView, boolean isUpBorder){
        super(gameView);
        this.status = Status.STANDARD;
        this.isUpBorder = isUpBorder;
        this.width = GameView.WIDTH / 3;
        this.height = 10;
        resetPosition();
        this.size = 7;
        this.hitBox.width = width;
        this.hitBox.height = height;
        gameView.setColorForBlockImage('b', Color.DARK_GRAY);
    }

    /**
     * Resets the position.
     */
    public void resetPosition(){
        if (isUpBorder) {
            this.position = new Position(0, -height);
        } else {
            this.position = new Position(0, GameView.HEIGHT + height);
        }
    }

    @Override
    public void updatePosition() {
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) position.x;
        hitBox.y = (int) position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
    }

    @Override
    public void updateStatus(){
        switch (status){
            case STANDARD:
                if (gameView.timerExpired("moved", "Cactus")) {
                    gameView.setTimer("moved", "Cactus", 5000);
                    status = Status.MOVED;
                }
                if (gameView.timerExpired("moved", "Cactus") && !isUpBorder) {
                    gameView.setTimer("moved", "Cactus", 5000);
                    status = Status.MOVED;
                }
                break;
            case MOVED:
                if (gameView.timerExpired("moved", "Cactus")) {
                    gameView.setTimer("moved", "Cactus", 5000);
                    status = Status.STANDARD;
                }
                break;
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.x, position.y, width, height,1, true, Color.blue.brighter());
    }
}