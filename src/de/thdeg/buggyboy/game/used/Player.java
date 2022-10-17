package de.thdeg.buggyboy.game.used;

/**
 * Represents the player of the game
 */
public class Player {
    /**
     * The number of lives, that the player starts with.
     */
    public static final int MAXIMUM_NUMBER_OF_LIVES = 3;
    /**
     * Current number of lives of the player.
     */
    public int lives;
    /**
     * Current score of the player.
     */
    public int score;
    /**
     * Current level of the player.
     */
    public Level level;

    /**
     * Creates a player.
     */
    public Player() {
        this.lives = MAXIMUM_NUMBER_OF_LIVES;
    }

}