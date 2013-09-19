package model;

import java.util.Arrays;

/**
 * Represents a cube of the puzzle, by its absolute position.
 * 
 * @author <a href="mailto:joffrey.bion@gmail.com">Joffrey Bion</a>
 */
public class Cube {

    int[] pos;

    /**
     * Returns the first {@code Cube} of the model, which is the starting point of
     * the representation.
     * 
     * @return The first {@code Cube} of the model.
     */
    static Cube getFirstCube() {
        return new Cube(0, 0, 0);
    }

    /**
     * Returns the second {@code Cube} of the model, which is next to the first cube.
     * 
     * @return The second {@code Cube} of the model.
     */
    static Cube getSecondCube() {
        return new Cube(1, 0, 0);
    }

    /**
     * Creates a new {@code Cube} with the specified coordinates.
     * 
     * @param coordinates
     *            The absolute coordinates of this cube.
     */
    Cube(int... coordinates) {
        this.pos = coordinates;
    }

    /**
     * Creates a new {@code Cube} with the specified {@code Cube}'s coordinates.
     * 
     * @param cube
     *            The cube to take the coordinates from.
     */
    Cube(Cube cube) {
        this.pos = Arrays.copyOf(cube.pos, cube.pos.length);
    }

    /**
     * Adds the specified value to the specified axis of this cube.
     * 
     * @param axis
     *            The axis to add {@code value} to.
     * @param value
     *            The value to add to {@code axis}.
     * @return This cube.
     */
    Cube add(int axis, int value) {
        this.pos[axis] += value;
        return this;
    }

    /**
     * Returns the distance from this {@code Cube} to the specified {@code Cube}, in
     * a vector from.
     * 
     * @param other
     *            Another {@code Cube} to get the distance to.
     * @return The distance from this {@code Cube} to {@code other}, on each axis.
     *         The values may be negative depending on the relative position of this
     *         {@code Cube} with respect to 5.
     */
    private int[] vectorDistanceTo(Cube other) {
        int[] distVect = new int[pos.length];
        for (int i = 0; i < pos.length; i++) {
            distVect[i] = other.pos[i] - pos[i];
        }
        return distVect;
    }

    /**
     * Returns the distance from this {@code Cube} to the specified {@code Cube}.
     * 
     * @param other
     *            Another {@code Cube} to get the distance to.
     * @return The distance from this {@code Cube} to {@code other}.
     */
    private int distanceTo(Cube other) {
        int[] distVect = vectorDistanceTo(other);
        int dist = 0;
        for (int i = 0; i < pos.length; i++) {
            dist += Math.abs(distVect[i]);
        }
        return dist;
    }

    /**
     * Gives the direction to the specified other cube. This method must not be
     * called on a cube that is not net to this cube.
     * 
     * @param other
     *            Another cube to get the direction to.
     * @return An array {@code direction} such that:
     *         <ul>
     *         <li>{@code direction[0]} is the axis to follow to find {@code other}</li>
     *         <li>{@code direction[1]} is either 1 or -1, depending on the relative
     *         position of {@code other} on the axis with respect to this cube</li>
     *         </ul>
     */
    int[] directionTo(Cube other) {
        if (distanceTo(other) != 1) {
            throw new RuntimeException(
                    "Cannot get the direction to a cube that is not next to this cube.");
        }
        int[] distVect = vectorDistanceTo(other);
        for (int i = 0; i < pos.length; i++) {
            if (distVect[i] != 0) {
                return new int[] { i, distVect[i] };
            }
        }
        throw new RuntimeException("IMPOSSIBRU! INTERNAL ERROR");
    }

    @Override
    public String toString() {
        return "(" + pos[0] + ", " + pos[1] + ", " + pos[2] + ")";
    }
}
