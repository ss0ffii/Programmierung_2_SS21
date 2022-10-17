package de.thdeg.buggyboy.graphics.movable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.CollidingGameObject;
import de.thdeg.buggyboy.graphics.bases.MovingGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;
import de.thdeg.buggyboy.graphics.movable.shots.BeetleShot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a spider in the game.
 */
public class Spider extends CollidingGameObject implements MovingGameObject {

    private final static String SPIDER_BLOCK_IMAGE =
            """
                    B    B
                     B  B\s
                      BB \s
                      BB \s
                     B  B\s
                    """;
    private final Random random;
    private Position targetPosition;
    private final MovementPatterns movementPatterns;
    private ArrayList<Position> movementPattern;
    private int currentPosition;

    /**
     * Crates a new spider.
     * @param gameView              GameView to show the spider on.
     * @param objectsToCollideWith  Objects that the spider can collide with.
     */
    public Spider(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith){
        super(gameView, objectsToCollideWith);
        this.random = new Random();
        this.position = new Position( (int) (GameView.WIDTH/2 + random.nextInt( 250)),  (int) (random.nextInt( GameView.HEIGHT)));
        this.speedInPixel = 1;
        this.size = 3;
        this.movementPatterns = new MovementPatterns();
        this.movementPattern = movementPatterns.getPattern("zigzag");
        this.targetPosition = movementPattern.get(0);
        this.currentPosition = 0;
        this.rotation = 0;
        this.width = (int) (5 * size);
        this.height = (int) (3 * size);
        gameView.setColorForBlockImage('B', Color.BLACK);
        this.hitBox.width = 17;
        this.hitBox.height = 16;
        this.objectID = "Spider" + position.x + position.y;
    }


    @Override
    public void updatePosition() {
        double distance = position.distance(targetPosition);
        if (distance >= speedInPixel){
            position.right((targetPosition.x - position.x) / distance * speedInPixel);
            position.down((targetPosition.y - position.y) / distance * speedInPixel);
        } else {
            setNewTargetPosition();
        }
    }

    /** Sets a new target position to move to. */
    public void setNewTargetPosition(){
        if (currentPosition < movementPattern.size() - 1) {
            currentPosition++;
        } else {
            currentPosition = 0;
            movementPattern = movementPatterns.getPattern("zigzag");
        }
        targetPosition = movementPattern.get(currentPosition);
    }

    @Override
    protected void updateHitBoxPosition(){
        hitBox.x = (int) (position.x);
        hitBox.y = (int) (position.y);
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject){
        if (otherObject.getClass() == BeetleShot.class){
            gamePlayManager.destroy(this);
        }
    }

    @Override
    public void updateStatus() {
        shoot();
    }

    private void shoot(){
        if (gameView.timerExpired("Shoot", objectID)){
            gameView.setTimer("Shoot", objectID, 10000);
            Position cobwebPosition = position.clone();
            cobwebPosition.x += width/2d;
            cobwebPosition.y += height;
            gamePlayManager.shootCobweb(cobwebPosition);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SPIDER_BLOCK_IMAGE, position.x, position.y, size, rotation);
    }
}