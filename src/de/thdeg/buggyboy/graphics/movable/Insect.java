package de.thdeg.buggyboy.graphics.movable;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.CollidingGameObject;
import de.thdeg.buggyboy.graphics.bases.MovingGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

/**
 * Represents an insect in the game.
 */
public class Insect extends CollidingGameObject implements MovingGameObject {

    private enum Status {STANDARD, SPEEDED}
    private final static String INSECT_BLOCK_IMAGE =
                    "    B       B    \n"+
                    "     B     B     \n"+
                    "      B   B      \n"+
                    "        B        \n"+
                    "      BCCCB      \n"+
                    "     BCCCCCB     \n"+
                    "    BCCCCCCCB    \n"+
                    "    BCCCCCCCB    \n"+
                    "   BBCCCCCCCBB   \n"+
                    "    BBBBBBBBB   \n"+
                    "      B  B      \n"
                    ;
    private final Random random;
    private final Status status;
    private boolean flyUpOrDown;
    private boolean stopMovingWhenCrashedOnBeetle;

    /**
     * Crates a new insect.
     *
     * @param gameView              GameView to show the insect on.
     * @param collidableGameObjects Objects, the insect can actively collide with.
     */
    public Insect(GameView gameView, ArrayList<CollidableGameObject> collidableGameObjects){
        super(gameView, collidableGameObjects);
        this.random = new Random();
        this.status = Status.STANDARD;
        initializeRandomSpawnPosition();
        this.speedInPixel = 4;
        this.size = 3;
        this.rotation = 0;
        this.width = (int) (5 * size);
        this.height = (int) (3 * size);
        gameView.setColorForBlockImage('C', Color.cyan.brighter());
        gameView.setColorForBlockImage('B', Color.BLACK);
        this.hitBox.width = 31;
        this.hitBox.height = 32;
        this.objectID = "Insect" + position.x + position.y;
    }

    private void initializeRandomSpawnPosition(){
        if (random.nextBoolean()){
            this.position = new Position(GameView.WIDTH/2 + random.nextInt(450) - width, GameView.WIDTH/2 + random.nextInt(200));
            this.flyUpOrDown = true;
        } else {
            this.position = new Position( GameView.WIDTH/2 + random.nextInt(350) - width, GameView.WIDTH/2 + random.nextInt(300));
            this.flyUpOrDown = false;
        }
    }

    @Override
    public void updatePosition() {
        if (flyUpOrDown) {
            if (position.y + height <= GameView.HEIGHT - 20) {
                position.down();
            } else {
                flyUpOrDown = false;
                position.up();
            }
        } else {
            if (position.y >= 30) {
                position.up();
            } else {
                flyUpOrDown = true;
                position.down();
            }
        }
    }

    @Override
    protected void updateHitBoxPosition(){
        hitBox.x = (int) (position.x + 10);
        hitBox.y = (int) (position.y);
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject){
        if (otherObject.getClass() == Beetle.class){
            stopMovingWhenCrashedOnBeetle = true;
        } else if (status == Status.STANDARD){
            gamePlayManager.destroy(this);
        }
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case STANDARD -> tryToShoot();
        }
    }

    private void tryToShoot() {
        if (random.nextInt(1000) < 5 && gameView.timerExpired("Shoot", objectID)) {
            shoot();
        }
    }

    private void shoot(){
        Position ballPosition = position.clone();
        double shotStartLocation = flyUpOrDown ? height/400d : -height/400d;
        ballPosition.x += height/2d + shotStartLocation;
        ballPosition.y += width;
        gamePlayManager.shootBall(flyUpOrDown, speedInPixel, ballPosition);
        gameView.setTimer("Shoot", objectID, 300);
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(INSECT_BLOCK_IMAGE, position.x, position.y, size, rotation);
    }
}