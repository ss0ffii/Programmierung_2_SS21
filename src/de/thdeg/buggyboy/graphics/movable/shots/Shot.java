package de.thdeg.buggyboy.graphics.movable.shots;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.graphics.bases.CollidingGameObject;
import de.thdeg.buggyboy.graphics.bases.MovingGameObject;
import de.thdeg.buggyboy.graphics.bases.Position;

import java.util.ArrayList;

/**
 * A new shot.
 */
public abstract class Shot extends CollidingGameObject implements MovingGameObject {

    /**
     * Crates a new shot, either of a plane, of an ghost or cobweb of spider.
     * @param objectsToCollideWith     A list of objects, that can be hit by this object.
     * @param gameView                 GameView to show the shot on.
     * @param position                 The position of this shot.
     */
    protected Shot(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, Position position) {
        super(gameView, objectsToCollideWith);
        this.position = position;
    }
}