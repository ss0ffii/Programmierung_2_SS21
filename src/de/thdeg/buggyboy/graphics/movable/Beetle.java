package de.thdeg.buggyboy.graphics.movable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.CollidingGameObject;
import de.thdeg.buggyboy.graphics.bases.MovingGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;
import de.thdeg.buggyboy.graphics.immovable.Border;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a beetle. The plane is the game object that is directly controlled by the user.
 */
public class Beetle extends CollidingGameObject implements MovingGameObject {

    private enum Status {STANDARD, DEAD}

    private Status status;
    private final static String BEETLE_BLOCK_IMAGE =
            """
                    P     P
                     PPPPP\s
                     PPPPP\s
                     PPPPP\s
                    PPPPPPP
                    P     P
                    """;
    private boolean movingDown;
    private boolean movingUp;
    private final double shotsPerSecond;
    protected boolean beetleIsMoving;
    private boolean shaking;

    /**
     * Creates a new beetle.
     *
     * @param gameView             GameView to show the shot on.
     * @param objectsToCollideWith Objects that the beetle can collide with.
     */
    public Beetle(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.status = Status.STANDARD;
        this.position = new Position(GameView.WIDTH / 10, GameView.HEIGHT / 2);
        this.speedInPixel = 7;
        this.size = 3;
        this.rotation = 0;
        this.width = 50;
        this.height = 50;
        this.shotsPerSecond = 10;
        this.hitBox.width = 20;
        this.hitBox.height = 18;
        beetleIsMoving = false;
        gameView.setColorForBlockImage('P', Color.MAGENTA.darker());
    }

    /**
     * Resets the beetle fot the next level.
     *
     * @param resetPosition Determines if the position should be reset to the original position.
     */
    public void resetBeetle(boolean resetPosition) {
        gameView.setTimer("Reset", "Beetle", 500);
        if (resetPosition) {
            position = new Position(GameView.WIDTH / 10, GameView.HEIGHT / 2);
        }
    }

    /**
     * Makes the beetle invisible. It can't be hit by shots.
     */
    public void setInvisible() {
        status = Status.DEAD;
    }

    /**
     * The beetle will go to the left, if possible.
     */
    public void left() {
        position.left(speedInPixel);
        beetleIsMoving = true;
        if (status == Status.STANDARD && position.x < 0){
            position.right(speedInPixel);
        }
    }

    /**
     * The beetle will go to the right, if possible.
     */
    public void right() {
        if (status == Status.STANDARD && position.x > GameView.WIDTH / 3 - 10) {
            beetleIsMoving = true;
            position.left(speedInPixel);
        } else {
            beetleIsMoving = true;
            position.right(speedInPixel);
        }
    }

    /**
     * The beetle will go to up, if possible.
     */
    public void up() {
        movingUp = true;
        if (status == Status.STANDARD && !collidesWith(objectsToCollideWith.get(0))) {
            beetleIsMoving = true;
            if (position.y > GameView.HEIGHT - height) {
                position.up(speedInPixel);
            } else {
                gamePlayManager.beetleMovingUp(speedInPixel);
            }
        }
    }

    /**
     * The beetle will go down, if possible.
     */
    public void down() {
        movingDown = true;
        if (status == Status.STANDARD && !collidesWith(objectsToCollideWith.get(1))) {
            beetleIsMoving = true;
            if (position.y > GameView.HEIGHT - height) {
                position.down(speedInPixel);
            } else {
                gamePlayManager.beetleMovingDown(speedInPixel);
            }
        }
    }

    /**
     * Shoot a bullet, if possible.
     */
    public void shoot() {
        if (gameView.timerExpired("Shot", "Beetle") && status == Status.STANDARD) {
            Position planeShotStartPosition = position.clone();
            planeShotStartPosition.x += width / 2d + 1;
            gamePlayManager.shootPlaneShot(planeShotStartPosition);
            gameView.setTimer("ShowCanon", "Beetle", (int) (500 / shotsPerSecond));
            gameView.setTimer("Shot", "Beetle", (int) (1000 / shotsPerSecond));
        }
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) (position.x);
        hitBox.y = (int) (position.y);
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() != Border.class) {
            if (status == Status.STANDARD) {
                this.status = Status.DEAD;
                gameView.setTimer("Dead", "Beetle", 1000);
                gamePlayManager.destroyBeetle();
            }
        }
    }

    @Override
    public void updatePosition() {
        // Nothing, because object is controlled directly by the user.
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD:
                movingAnimation();
                break;
            case DEAD:
                if (gameView.timerExpired("Dead", "Beetle")) {
                    status = Status.STANDARD;
                }
                break;
        }
    }

    private void movingAnimation() {
        if (beetleIsMoving) {
            position.y = shaking ? position.y - 3 : position.y + 3;
            position.x = shaking ? position.x - 3 : position.x + 3;
            shaking = !shaking;
            beetleIsMoving = false;
        } else {
            shaking = !shaking;
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(BEETLE_BLOCK_IMAGE, position.x, position.y, size, rotation);
    }
}