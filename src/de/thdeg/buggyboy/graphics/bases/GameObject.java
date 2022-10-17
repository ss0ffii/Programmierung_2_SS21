package de.thdeg.buggyboy.graphics.bases;

import de.thdeg.buggyboy.game.managers.GamePlayManager;
import de.thdeg.buggyboy.gameview.GameView;

import java.util.Objects;

/**
 * Represents a movable and displayable game object.
 */
public abstract class GameObject implements Cloneable {

    protected final GameView gameView;
    protected GamePlayManager gamePlayManager;
    protected Position position;
    protected double speedInPixel;
    protected double rotation;
    protected double size;
    protected int width;
    protected int height;
    protected String objectID;

    /**
     * Creates the GameObject with the GameView to be displayed on.
     *
     * @param gameView Window to show the GameObject on.
     */
    protected GameObject(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position();
    }

    /**
     * Updates the game object.
     */
    public void update() {
        if (this instanceof MovingGameObject) {
            ((MovingGameObject) this).updatePosition();
        }
        updateStatus();
    }

    /**
     * Used to update the status of a game object.
     */
    protected abstract void updateStatus();

    /**
     * Used to draw the game object to the canvas of GameView.
     */
    public abstract void addToCanvas();

    /**
     * Sets the GamePlayManager, so the game object is able call game-play methods.
     *
     * @param gamePlayManager GamePlayManager that controls this game.
     */
    public void setGamePlayManager(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    /**
     * Adapts the position of this game object, in case the whole game world is moved.
     *
     * @param adaptX Adaption to the right.
     * @param adaptY Adaption downwards.
     */
    public void adaptPosition(double adaptX, double adaptY) {
        position.x += adaptX;
        position.y += adaptY;
    }

    /**
     * Returns the current position of this game object, consisting of x- and y-coordinates {@link Position}.
     *
     * @return Current position.
     * @see Position
     */
    public Position getPosition() {
        return position;
    }

    @Override
    public GameObject clone(){
        GameObject gameObject = null;
        try {
            gameObject = (GameObject) super.clone();
            gameObject.position = position.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return gameObject;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return Double.compare(that.speedInPixel, speedInPixel) == 0
                && Double.compare(that.rotation, rotation) == 0
                && Double.compare(that.size, size) == 0
                && width == that.width
                && height == that.height
                && position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, speedInPixel, rotation, size, width, height);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + ": " + position;
    }
}