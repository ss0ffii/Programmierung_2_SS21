package de.thdeg.buggyboy.game.used;

import de.thdeg.buggyboy.gameview.GameView;

/**
 * A countdown for the level.
 */
public class Countdown {

    private long countDownLength;
    private long timeLeft;
    private long startTime;
    private boolean stopCountdown;
    private final GameView gameView;

    /**
     * Creates this countdown.
     *
     * @param gameView The gameView to display the gameObjects on.
     */
    public Countdown(GameView gameView){
        this.gameView = gameView;
    }

    /**
     * The countdown is set to a start value and immediately started.
     *
     * @param countDownLength The length of the countdown.
     */
    public void startCountdown(int countDownLength) {
        this.startTime = gameView.getGameTimeInMilliseconds();
        this.countDownLength = countDownLength * 1000L;
        this.timeLeft = this.countDownLength;
        this.stopCountdown = false;
    }

    /**
     * Returns the time that is left from the countdown.
     *
     * @return The time that left.
     */
    public int getTimeLeft(){
        if (!stopCountdown && timeLeft > 0){
            timeLeft = countDownLength - (gameView.getGameTimeInMilliseconds() - startTime);
        }
        return (int) Math.ceil(timeLeft / 1000d);
    }

    /**
     * Stops the countdown.
     */
    public void stop(){
        stopCountdown = true;
    }
}