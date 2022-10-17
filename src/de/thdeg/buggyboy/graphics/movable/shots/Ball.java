package de.thdeg.buggyboy.graphics.movable.shots;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;
import java.util.ArrayList;

/**
 * A ball.
 */
public class Ball extends Shot {

    private enum Status {STANDARD, SPEEDED}

    private final boolean flyUpOrDown;
    private Status status;
    private double sinkingSpeed;

    /**
     * Creates a ball.
     *
     * @param gameView                 GameView to show the ball on.
     * @param objectsToCollideWith     A list of objects, that can be hit by this object.
     * @param flyUpOrDown              Determines if the insect that shoots is currently flying to the up (or the down).
     * @param speedInPixel             Current speed of insect that shoots.
     * @param position                 The position of this ball.
     */
    public Ball(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, boolean flyUpOrDown, double speedInPixel, Position position) {
        super(gameView, objectsToCollideWith, position);
        this.flyUpOrDown = flyUpOrDown;
        this.speedInPixel = speedInPixel;
        this.status = Status.STANDARD;
        this.sinkingSpeed = 1;
        this.size = 2;
        this.rotation = 0;
        this.width = 10;
        this.height = 10;
        this.hitBox.width = width;
        this.hitBox.height = height;
        this.objectID = "Ball" + position.x + position.y;
    }

    @Override
    public void updatePosition() {
        position.left(sinkingSpeed);
        switch (status) {
            case STANDARD:
                if (flyUpOrDown) {
                    position.down(0.5);
                } else {
                    position.up(0.5);
                }
                break;
            case SPEEDED:
                speededAnimation();
                break;
        }
    }

    private void speededAnimation(){
        if (position.x < (int) (GameView.HEIGHT / 2 + 100)){
            sinkingSpeed += 2;
        }
    }

    @Override
    protected void updateHitBoxPosition(){
        hitBox.x = (int) position.x;
        hitBox.y = (int) position.y;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject){
        gamePlayManager.destroy(this);
    }

    @Override
    public void updateStatus() {
        if (position.x < 5) {
            gamePlayManager.destroy(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x + size, position.y + size, 3 * size, 2 * size, 0, true, Color.cyan.brighter());
        gameView.addOvalToCanvas(position.x + size, position.y + size, 3 * size, 2 * size, 0.5, false, Color.black);
    }
}