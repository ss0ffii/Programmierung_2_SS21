package de.thdeg.buggyboy.game.managers;

import de.thdeg.buggyboy.game.used.Level;
import de.thdeg.buggyboy.graphics.immovable.Background;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the levels.
 */
class LevelManager {

    private ArrayList<Level> levels;
    private int nextLevel;

    LevelManager(boolean difficultyIsSetToEasy) {
        int numberOfGhosts = 2;
        int numberOfSpiders = 1;
        if (difficultyIsSetToEasy){
            numberOfGhosts += 1;
        } else {
            numberOfGhosts += 1;
            numberOfSpiders += 2;
        }
        Level level1 = new Level("Level 1", Background.Style.LIGHT, numberOfGhosts, numberOfSpiders);
        Level level2 = new Level("Level 2", Background.Style.DARK, numberOfGhosts, numberOfSpiders);
        this.levels = new ArrayList<>(List.of(level1, level2));
        this.nextLevel = 0;
    }

    /**
     * Determines, if there is next level.
     *
     * @return <code>true</code> if exists one.
     */
    public boolean hasNextLevel(){
        return nextLevel < levels.size();
    }

    /**
     * Returns the next level.
     *
     * @return The next level
     * @throws NoMoreLevelsAvailableException if there isn't next level that's available.
     */
    public Level getNextLevel(){
        Level level = levels.get(nextLevel);
        if(level == null){
            throw new NoMoreLevelsAvailableException();
        }
        nextLevel++;
        return level;
    }
}