package de.thdeg.buggyboy.game.used;

import de.thdeg.buggyboy.graphics.immovable.Background;

/**
 * A level of the game.
 */
public class Level {

    /**
     * Name of the level.
     */
    public final String name;
    /**
     * Background of the level
     */
    public final Background.Style backgroundStyle;
    /**
     * Number of ghosts in the created level.
     */
    public final int numberOfGhosts;

    /**
     * Number of spiders in the created level.
     */
    public final int numberOfSpiders;

    /**
     * Creates a level
     *
     * @param name            Name of the level.
     * @param backgroundStyle Background of the level
     * @param numberOfGhosts  Number of ghosts in the level.
     * @param numberOfSpiders Number of spiders in the level.
     */
    public Level(String name, Background.Style backgroundStyle, int numberOfGhosts, int numberOfSpiders) {
        this.name = name;
        this.backgroundStyle = backgroundStyle;
        this.numberOfGhosts = numberOfGhosts;
        this.numberOfSpiders = numberOfSpiders;
    }
}