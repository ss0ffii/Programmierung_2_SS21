package de.thdeg.buggyboy.graphics.bases;

import java.util.Objects;

/**
 * The position of a game object with x- and y-coordinate.
 */
public class Position implements Cloneable, Comparable<Position> {

    /**
     * X-coordinate on the window.
     */
    public double x;

    /**
     * Y-coordinate on the window.
     */
    public double y;

    /**
     * Creates a position on (0, 0).
     */
    public Position(){
        this(0, 0);
    }

    /**
     * Creates a position on (x, y).
     *
     * @param x X-coordinate on the window.
     * @param y Y-coordinate on the window.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * One pixel to the left.
     */
    public void left(){
        x--;
    }

    /**
     * To the left by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void left(double pixel){
        x -= pixel;
    }

    /**
     * One pixel to the right.
     */
    public void right(){
        x++;
    }

    /**
     * To the right by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void right(double pixel){
        x += pixel;
    }

    /**
     * One pixel upwards.
     */
    public void up(){
        y--;
    }

    /**
     * Upwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void up(double pixel){
        y -= pixel;
    }

    /**
     * One pixel downwards.
     */
    public void down() {
        y++;
    }

    /**
     * Downwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void down(double pixel) {
        y += pixel;
    }

    @Override
    public String toString(){
        return ("Position (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")");
    }

    /**
     * Calculates the distance to any other position.
     *
     * @param other Position to calculate the distance to.
     * @return The distance.
     */
    public double distance(Position other){
        return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
    }

    @Override
    public Position clone(){
        Position clone = null;
        try {
            clone = (Position) super.clone();
        } catch (CloneNotSupportedException ignored){
        }
        return clone;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Position other) {
        return (int) Math.signum(distance(new Position()) - other.distance(new Position()));
    }
}