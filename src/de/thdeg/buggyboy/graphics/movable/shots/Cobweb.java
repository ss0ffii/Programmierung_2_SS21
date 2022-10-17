package de.thdeg.buggyboy.graphics.movable.shots;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A cobweb.
 */
public class Cobweb extends Shot {

    private enum Status {STANDARD, PLACED}

    private final Random random;
    private Status status;
    private Position targetPosition;
    private final static String COBWEB_BLOCK_IMAGE =
            """
                    B B
                     B\s
                    B B
                    """;

    /**
     * Creates a cobweb.
     *
     * @param gameView                 GameView to show the cobweb on.
     * @param objectsToCollideWith     A list of objects, that can be hit by this object.
     * @param position                 The position of this cobweb.
     */
    public Cobweb(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, Position position) {
        super(gameView, objectsToCollideWith, position);
        this.speedInPixel = 3;
        this.status = Status.STANDARD;
        this.random = new Random();
        this.targetPosition = new Position(random.nextInt(GameView.WIDTH / 3), random.nextInt(GameView.HEIGHT));
        this.size = 2;
        this.rotation = 0;
        this.width = 5;
        this.height = 5;
        this.objectID = "Cobweb" + position.x + position.y;
    }

    @Override
    public void updatePosition() {
        setTargetPosition();
    }

    private void setTargetPosition(){
        double distance = position.distance(targetPosition);
        if (distance >= speedInPixel){
            position.right((targetPosition.x - position.x) / distance * speedInPixel);
            position.down((targetPosition.y - position.y) / distance * speedInPixel);
        } else {
            status = Status.PLACED;
        }
    }

    @Override
    protected void updateHitBoxPosition(){
        if (status == Status.STANDARD){
            hitBox.x = (int) position.x;
            hitBox.y = (int) position.y;
            hitBox.width = width;
            hitBox.height = height;
        } else if (status == Status.PLACED){
            hitBox.x = (int) position.x;
            hitBox.y = (int) position.y;
            hitBox.width = 14;
            hitBox.height = 14;
        }
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject){
        gamePlayManager.destroy(this);
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD:
                break;
            case PLACED:
                if (gameView.timerExpired("placed", "Cobweb")) {
                    gameView.setTimer("placed", "Cobweb", 5000);
                    gamePlayManager.destroy(this);
                }
                break;
        }
    }

    @Override
    public void addToCanvas() {
        if (status == Status.STANDARD) {
            gameView.addOvalToCanvas(position.x + size, position.y + size, 3 * size, 2 * size, 0, true, Color.black);
        } else if (status == Status.PLACED) {
            gameView.addBlockImageToCanvas(COBWEB_BLOCK_IMAGE, position.x, position.y, size + 3, rotation);
        }
    }
}