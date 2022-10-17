package de.thdeg.buggyboy.graphics.movable.shots;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.awt.*;
import java.util.ArrayList;

/**
 * A shot of the beetle.
 */
public class BeetleShot extends Shot {

    /**
     * Crates a new shot, either of a beetle.
     *
     * @param gameView                 GameView to show the shot on.
     * @param objectsToCollideWith     A list of objects, that can be hit by this object.
     * @param position                 The position of this shot.
     */
    public BeetleShot(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, Position position){
        super(gameView, objectsToCollideWith, position);
        this.size = 2;
        this.width = 10;
        this.height = 10;
        this.speedInPixel = 2;
        this.hitBox.width = width;
        this.hitBox.height = height;
    }

    @Override
    public void updatePosition() {
        position.right(speedInPixel);
    }

    @Override
    public void updateHitBoxPosition(){
        hitBox.x = (int) (position.x - size);
        hitBox.y = (int) (position.y - (2 * size));
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject){
        gamePlayManager.destroy(this);
    }

    @Override
    public void updateStatus() {
        destroyShotIfItHasLeftTheScreen();
    }

    private void destroyShotIfItHasLeftTheScreen(){
        if (position.x < 0){
            gamePlayManager.destroy(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x, position.y, width, height, 1, false, Color.MAGENTA.darker());
    }
}