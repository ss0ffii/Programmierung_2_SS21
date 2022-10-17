package de.thdeg.buggyboy.graphics.bases;

import de.thdeg.buggyboy.gameview.GameView;

import java.awt.*;
import java.util.Objects;

/**
 * Represents all game objects that are able to collide with something.
 */
public abstract class CollidableGameObject extends GameObject {

    protected Rectangle hitBox;

    protected CollidableGameObject(GameView gameView) {
        super(gameView);
        this.hitBox = new Rectangle(-100_000, -100_000, 0, 0);
    }

    @Override
    public void update() {
        super.update();
        updateHitBoxPosition();
    }

    /**
     * Used to update the hitBox position of the game object.
     */
    protected abstract void updateHitBoxPosition();

    @Override
    public void adaptPosition(double adaptX, double adaptY) {
        super.adaptPosition(adaptX, adaptY);
        updateHitBoxPosition();
    }

    /**
     * If a GameObject has collided with something, it is able to react to the collision.
     *
     * @param otherObject The other GameObject that is involved in the collision.
     */
    public abstract void reactToCollision(CollidableGameObject otherObject);

    @Override
    public CollidableGameObject clone(){
        return (CollidableGameObject) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CollidableGameObject that = (CollidableGameObject) o;
        return Objects.equals(hitBox, that.hitBox);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),hitBox);
    }
}