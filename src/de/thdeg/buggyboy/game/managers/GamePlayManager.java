package de.thdeg.buggyboy.game.managers;

import de.thdeg.buggyboy.game.used.Countdown;
import de.thdeg.buggyboy.gameview.GameView;
import de.thdeg.buggyboy.graphics.bases.CollidableGameObject;
import de.thdeg.buggyboy.game.used.Level;
import de.thdeg.buggyboy.game.used.Player;
import de.thdeg.buggyboy.graphics.bases.Position;
import de.thdeg.buggyboy.graphics.movable.Spider;
import de.thdeg.buggyboy.graphics.movable.shots.Ball;
import de.thdeg.buggyboy.graphics.movable.Insect;
import de.thdeg.buggyboy.graphics.movable.shots.Cobweb;
import de.thdeg.buggyboy.graphics.movable.shots.BeetleShot;
import de.thdeg.buggyboy.graphics.movable.shots.Shot;
import de.thdeg.buggyboy.graphics.screens.EndScreen;
import de.thdeg.buggyboy.graphics.screens.StartScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the game play.
 */
public class GamePlayManager {

    private final static int COUNTDOWN_LENGTH = 20;
    private final GameView gameView;
    private final GameObjectManager gameObjectManager;
    private Countdown countdown;
    private final Player player;
    private LevelManager levelManager;
    private boolean levelOver;
    private boolean gameOver;

    GamePlayManager(GameView gameView, GameObjectManager gameObjectManager) {
        this.gameView = gameView;
        this.gameObjectManager = gameObjectManager;
        this.gameObjectManager.getBeetle().setGamePlayManager(this);
        this.player = new Player();
        initializeGame();
    }

    private void initializeGame(){
        StartScreen startScreen = new StartScreen(gameView);
        startScreen.showStartScreen();
        levelManager = new LevelManager(startScreen.isDifficultySetToEasy());
        this.player.score = 0;
        this.player.lives = Player.MAXIMUM_NUMBER_OF_LIVES;
        initializeLevel();
    }

    private void initializeLevel(){
        countdown = new Countdown(gameView);
        countdown.startCountdown(COUNTDOWN_LENGTH);
        Level level = levelManager.getNextLevel();
        player.level = level;
        gameObjectManager.getInsects().clear();
        gameObjectManager.getBalls().clear();
        gameObjectManager.getSpiders().clear();
        gameObjectManager.getCobwebs().clear();
        gameObjectManager.getBeetleShots().clear();
        gameObjectManager.getBackground().setStyle(level.backgroundStyle);
        gameObjectManager.getLivesPanel().setLives(player.lives);
        gameObjectManager.getScorePanel().setScore(0);
        gameObjectManager.getBeetle().resetBeetle(true);
        gameObjectManager.getBackground().resetPosition();
        gameObjectManager.getUpBorder().resetPosition();
        gameObjectManager.getDownBorder().resetPosition();
        gameObjectManager.getOverlay().showMessage(level.name);
    }

    private void nextGame(){
        if(!gameOver){
            gameView.setTimer("New game", "GamePlayManager", 3000);
            gameOver = true;
            gameObjectManager.getBeetle().setInvisible();
            gameObjectManager.getOverlay().showMessage("The game ends!");
            countdown.stop();
        }
        if(gameView.timerExpired("New game", "GamePlayManager")){
            gameOver = false;
            EndScreen endScreen = new EndScreen(gameView);
            endScreen.showEndScreen(player.score);
            initializeGame();
        }
    }

    private void nextLevel(){
        if(!levelOver){
            gameView.setTimer("Next level", "GamePlayManager", 3000);
            levelOver = true;
            gameObjectManager.getBeetle().setInvisible();
            gameObjectManager.getOverlay().showMessage("Super!");
            countdown.stop();
        }
        if(gameView.timerExpired("Next level", "GamePlayManager")){
            levelOver = false;
            initializeLevel();
        }
    }

    /**
     * This method manages the game play. New enemies are spawned and new levels or games are initialized.
     */
    void updateGamePlay(){
        gameObjectManager.getCountdownPanel().setTimeLeft(countdown.getTimeLeft());
        if (player.lives == 0){
            nextGame();
        } else if (countdown.getTimeLeft() <= 0) {
            if (levelManager.hasNextLevel()) {
                nextLevel();
            } else {
                nextGame();
            }
        } else {
            spawnGhosts();
            spawnSpiders();
        }
    }

    /**
     * Is called when the beetle has bin hit by a shot. Determines, if the game is over.
     */
    public void destroyBeetle(){
        player.lives--;
        gameObjectManager.getLivesPanel().setLives(player.lives);
        gameObjectManager.getBeetle().resetBeetle(false);
    }

    /**
     * Adds a shot of the plane, so it will be displayed on the window.
     * @param startPosition The position to spawn the shot from.
     */
    public void shootPlaneShot(Position startPosition){
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>(40);
        collidableGameObjects.addAll(gameObjectManager.getInsects());
        collidableGameObjects.addAll(gameObjectManager.getBalls());
        collidableGameObjects.addAll(gameObjectManager.getSpiders());
        collidableGameObjects.addAll(gameObjectManager.getCobwebs());
        BeetleShot beetleShot = new BeetleShot(gameView, collidableGameObjects, startPosition);
        beetleShot.setGamePlayManager(this);
        gameObjectManager.getBeetleShots().add(beetleShot);
    }

    private void spawnSpiders() {
        gameView.setTimer("Spawn Spider", "Spider", 2000);
        if(gameObjectManager.getSpiders().size() < player.level.numberOfSpiders) {
            Spider spider = new Spider(gameView, new ArrayList<>(List.of(gameObjectManager.getBeetle())));
            spider.setGamePlayManager(this);
            gameObjectManager.getSpiders().add(spider);
        }
    }

    /**
     * Removes a GameObject from the list of game objects, so it will be not be displayed on the window anymore.
     * @param spider Object to be removed from the window.
     */
    public void destroy(Spider spider){
        gameObjectManager.getSpiders().remove(spider);
        player.score += 3;
        gameObjectManager.getScorePanel().setScore(player.score);
    }

    private void spawnGhosts(){
        if (gameObjectManager.getInsects().size() < player.level.numberOfGhosts) {
            Insect insect = new Insect(gameView, new ArrayList<>(List.of(gameObjectManager.getBeetle())));

            insect.setGamePlayManager(this);
            gameObjectManager.getInsects().add(insect);
        }
    }

    /**
     * Removes a GameObject from the list of game objects, so it will be not be displayed on the window anymore.
     * @param insect Object to be removed from the window.
     */
    public void destroy(Insect insect){
        gameObjectManager.getInsects().remove(insect);
        player.score += 1;
        gameObjectManager.getScorePanel().setScore(player.score);
    }

    /**
     * Adds a ball(the shot of a ghost), so it will be displayed on the window.
     *
     * @param toDown          Direction of the ghost that had shot.
     * @param speedInPixel    Speed of the ghost that had shot.
     * @param ballPosition    Start position of the shot.
     */
    public void shootBall(boolean toDown, double speedInPixel, Position ballPosition){
        Ball ball = new Ball(gameView, new ArrayList<>(List.of(gameObjectManager.getBeetle())), toDown, speedInPixel, ballPosition);
        ball.setGamePlayManager(this);
        gameObjectManager.getBalls().add(ball);
    }

    /**
     * Adds a cobweb, so it will be displayed on the window.
     * @param cobwebPosition  Start position of the cobweb.
     */
    public void shootCobweb(Position cobwebPosition){
        Cobweb cobweb = new Cobweb(gameView, new ArrayList<>(List.of(gameObjectManager.getBeetle())), cobwebPosition);
        cobweb.setGamePlayManager(this);
        gameObjectManager.getCobwebs().add(cobweb);
    }

    /**
     * Removes a GameObject(any kind of shot) from the list of game objects, so it will be not be displayed on the window anymore.
     * @param shot Object to be removed from the window.
     */
    public void destroy(Shot shot){
        if (shot.getClass() == Ball.class) {
            gameObjectManager.getBalls().remove(shot);
        } else if (shot.getClass() == BeetleShot.class) {
            gameObjectManager.getBeetleShots().remove(shot);
        } else if (shot.getClass() == Cobweb.class) {
            gameObjectManager.getCobwebs().remove(shot);
        }
    }

    /**
     * Is called when the plane has moved and the world needs to be shifted. The world will be shifted accordingly.
     *
     * @param pixels Number of pixels to shift the world on the y-Axis
     */
    public void beetleMovingUp(double pixels) {
        gameObjectManager.moveWorld(0, pixels);
    }

    /**
     * Is called when the plane has moved and the world needs to be shifted. The world will be shifted accordingly.
     *
     * @param pixels Number of pixels to shift the world on the y-Axis
     */
    public void beetleMovingDown(double pixels) {
        gameObjectManager.moveWorld(0, -pixels);
    }
}