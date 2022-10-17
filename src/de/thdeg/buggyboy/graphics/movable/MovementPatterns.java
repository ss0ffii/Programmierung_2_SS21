package de.thdeg.buggyboy.graphics.movable;

import de.thdeg.buggyboy.graphics.bases.Position;

import java.util.*;

/**
 * A class for a movement patterns.
 */
public class MovementPatterns {

    private final Random random;
    private final HashMap<String, ArrayList<Position>> movementPatterns;

    /**
     * Creates the MovementPatterns to be followed on.
     */
    public MovementPatterns(){
        this.random = new Random();
        this.movementPatterns = new HashMap<>(7);
        ArrayList<Position> zigZag = new ArrayList<>(List.of(new Position(550, 100),
                new Position(850, 150),
                new Position(550, 200),
                new Position(850, 250),
                new Position(550, 300),
                new Position(850, 350),
                new Position(550, 400),
                new Position(850, 400),
                new Position(550, 350),
                new Position(850, 300),
                new Position(550, 250),
                new Position(850, 200),
                new Position(550, 150),
                new Position(850, 100)));

        this.movementPatterns.put("zigzag", zigZag);
    }

    /**
     * Gets a certain pattern.
     * @param pattern The requested pattern as a String.
     * @return The requested pattern.
     */
    public ArrayList<Position> getPattern(String pattern) {
        return movementPatterns.get(pattern);
    }

}