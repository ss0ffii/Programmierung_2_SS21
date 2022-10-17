package de.thdeg.buggyboy.game.managers;

import de.thdeg.buggyboy.gameview.GameView;

/** This class manages the main game loop of the game. */
public class GameLoopManager {

    private final GameView gameView;
    private final InputManager inputManager;
    private final GameObjectManager gameObjectManager;
    private final GamePlayManager gamePlayManager;

    /** Creates the main loop. */
    public GameLoopManager() {
        this.gameView = new GameView();
        this.gameView.setWindowTitle("BuggyBoy");
        this.gameView.setStatusText("Gutoranska Sofia - Java Programmierung SS 2021");
        this.gameView.setWindowIcon("Target.png");
        this.gameObjectManager = new GameObjectManager(gameView);
        this.gamePlayManager = new GamePlayManager(gameView, gameObjectManager);
        this.inputManager = new InputManager(gameView, gameObjectManager.getBeetle());
    }

    /** Starts the main loop of the game. */
    public void startGame() {
        while(true) { // Der "Game-Loop"
            gamePlayManager.updateGamePlay();
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();
        }
    }
}