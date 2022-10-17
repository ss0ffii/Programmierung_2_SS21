package de.thdeg.buggyboy.game.managers;

/**
 * Exepiton is thrown, if there are no more levels available in the list of levels.
 */
public class NoMoreLevelsAvailableException extends RuntimeException {

    /**
     * Creates exception without parameters.
     */
    public NoMoreLevelsAvailableException() {
        super();
    }

    /**
     * Creates exception with message.
     */
    public NoMoreLevelsAvailableException(String message) {
        super(message);
    }
}