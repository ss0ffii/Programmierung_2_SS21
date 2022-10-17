package de.thdeg.buggyboy.game.managers;

import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.GameObject;
import de.thdeg.buggyboy.graphics.immovable.*;
import de.thdeg.buggyboy.graphics.movable.*;
import de.thdeg.buggyboy.graphics.movable.shots.Ball;
import de.thdeg.buggyboy.graphics.movable.shots.Cobweb;
import de.thdeg.buggyboy.graphics.movable.shots.BeetleShot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class manages all game objects of the program that are displayed on the GameView.
 */
class GameObjectManager {

    private final LinkedList<GameObject> gameObjects;
    private final LinkedList<Ball> balls;
    private final LinkedList<Cobweb> cobwebs;
    private final LinkedList<BeetleShot> beetleShots;
    private final LinkedList<Insect> insects;
    private final LinkedList<Spider> spiders;

    private final Beetle beetle;
    private final Background background;
    private final Border upBorder;
    private final Border downBorder;
    private final ScorePanel scorePanel;
    private final LivesPanel livesPanel;
    private final CountdownPanel countdownPanel;
    private final Overlay overlay;

    GameObjectManager(GameView gameView) {
        this.gameObjects = new LinkedList<>();
        this.insects = new LinkedList<>();
        this.balls = new LinkedList<>();
        this.cobwebs = new LinkedList<>();
        this.beetleShots = new LinkedList<>();
        this.spiders = new LinkedList<>();

        this.upBorder = new Border(gameView, true);
        this.downBorder = new Border(gameView, false);
        this.beetle = new Beetle(gameView, new ArrayList<>(List.of(upBorder, downBorder)));
        this.background = new Background(gameView);
        this.scorePanel = new ScorePanel(gameView);
        this.livesPanel = new LivesPanel(gameView);
        this.countdownPanel = new CountdownPanel(gameView);
        this.overlay = new Overlay(gameView);
    }

    /**
     * Updates the list of gameObjects. The order of adding the Objects plays an important role and determines foreground and background.
     */
    void updateGameObjects() {
        gameObjects.clear();

        gameObjects.add(background);
        gameObjects.add(upBorder);
        gameObjects.add(downBorder);
        gameObjects.add(scorePanel);
        gameObjects.add(livesPanel);
        gameObjects.add(countdownPanel);
        gameObjects.addAll(beetleShots);
        gameObjects.addAll(balls);
        gameObjects.addAll(cobwebs);
        gameObjects.addAll(insects);
        gameObjects.addAll(spiders);
        gameObjects.add(beetle);
        gameObjects.add(overlay);

        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            gameObject.addToCanvas();
        }
    }

    /**
     * This method moves the whole world according to the given adaption.
     *
     * @param adaptX Number of pixels to move the world on the x-axis.
     * @param adaptY Number of pixels to move the world on the y-axis.
     */
    public void moveWorld(double adaptX, double adaptY) {
        upBorder.adaptPosition(adaptX, adaptY);
        downBorder.adaptPosition(adaptX, adaptY);
        for (Insect insect : insects) {
            insect.adaptPosition(adaptX, adaptY);
        }
        for (Ball ball : balls) {
            ball.adaptPosition(adaptX, adaptY);
        }
        for (Spider spider : spiders){
            spider.adaptPosition(adaptX, adaptY);
        }
        for (Cobweb cobweb : cobwebs){
            cobweb.adaptPosition(adaptX, adaptY);
        }
        for (BeetleShot beetleShot : beetleShots){
            beetleShot.adaptPosition(adaptX, adaptY);
        }
    }

    LinkedList<Ball> getBalls() {
        return balls;
    }

    LinkedList<Cobweb> getCobwebs(){
        return cobwebs;
    }

    LinkedList<BeetleShot> getBeetleShots() {
        return beetleShots;
    }

    LinkedList<Insect> getInsects() {
        return insects;
    }

    LinkedList<Spider> getSpiders(){
        return spiders;
    }

    Beetle getBeetle() {
        return beetle;
    }

    Border getUpBorder(){
        return upBorder;
    }

    Border getDownBorder(){
        return downBorder;
    }

    ScorePanel getScorePanel(){
        return scorePanel;
    }

    LivesPanel getLivesPanel(){
        return livesPanel;
    }

    CountdownPanel getCountdownPanel(){
        return countdownPanel;
    }

    Background getBackground(){
        return background;
    }

    Overlay getOverlay(){
        return overlay;
    }
}