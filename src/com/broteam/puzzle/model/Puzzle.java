package com.broteam.puzzle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A list of placed {@link Cube}s, used to solve the puzzle.
 * 
 * @author <a href="mailto:joffrey.bion@gmail.com">Joffrey Bion</a>
 */
public class Puzzle extends ArrayList<Cube> {

    /**
     * The maximum size that the {@link #matrix} can reach.
     */
    private static int SIZE = 28;

    /**
     * In addition to the list that this {@code Puzzle} is, a 3-dimensional matrix is
     * needed to check for cube overlaps in the space.
     */
    private int[][][] matrix;
    /**
     * Contains the maximum position reached by the cubes on each axis.
     */
    private int[] max = new int[] { 0, 0, 0 };
    /**
     * Contains the minimum position reached by the cubes on each axis.
     */
    private int[] min = new int[] { 0, 0, 0 };

    /**
     * Create a new {@code Puzzle}, containing the first 2 cubes.
     */
    public Puzzle() {
        super();
        init();
        // the 2 first cubes are always in the same spot
        add(Cube.getFirstCube());
        add(Cube.getSecondCube());
    }

    /**
     * Create a copy of the specified puzzle.
     * 
     * @param p
     *            The original puzzle to take the cubes from.
     */
    public Puzzle(Puzzle p) {
        super();
        init();
        // the 2 first cubes are always in the same spot
        for (Cube c : p) {
            add(c);
        }
    }

    private void init() {
        // arrays automatically initialized to 0
        max = new int[3];
        min = new int[3];
        matrix = new int[SIZE][SIZE][SIZE];
    }

    /**
     * Adds the specified {@link Cube} to this {@code Puzzle}.
     * 
     * @param c
     *            The {@code Cube} to add.
     * @return {@code true}, as specified by {@link java.util.Collection#add(Object)}
     */
    @Override
    public boolean add(Cube c) {
        // adds the specified cube to the matrix
        int x = c.pos[0] + SIZE / 2;
        int y = c.pos[1] + SIZE / 2;
        int z = c.pos[2] + SIZE / 2;
        matrix[x][y][z]++;
        // update the min and max on each axis if the specified cube change them
        for (int i = 0; i < max.length; i++) {
            max[i] = Math.max(max[i], c.pos[i]);
            min[i] = Math.min(min[i], c.pos[i]);
        }
        // adds the specified cube to this list
        return super.add(c);
    }

    /**
     * Removes the specified {@link Cube} from this {@code Puzzle}.
     * 
     * @param c
     *            The {@code Cube} to remove.
     */
    public void remove(Cube c) {
        // removes the specified cube from the matrix
        int x = c.pos[0] + SIZE / 2;
        int y = c.pos[1] + SIZE / 2;
        int z = c.pos[2] + SIZE / 2;
        matrix[x][y][z]--;
        // removes the specified cube from this list
        super.remove(c);
        // if this cube was on the edge of this puzzle,
        // the min and max need to be updated
        for (int i = 0; i < max.length; i++) {
            if ((max[i] == c.pos[i]) || (min[i] == c.pos[i])) {
                recomputeMinMax();
                // no need to check the other edges if the in/ax are recomputed
                // anyway
                break;
            }
        }
    }

    /**
     * Recomputes the min and max position on each axis.
     */
    private void recomputeMinMax() {
        Arrays.fill(max, 0);
        Arrays.fill(min, 0);
        for (Cube c : this) {
            for (int i = 0; i < max.length; i++) {
                max[i] = Math.max(max[i], c.pos[i]);
                min[i] = Math.min(min[i], c.pos[i]);
            }
        }
    }

    /**
     * Returns whether this {@code Puzzle} is in an acceptable state. A state is
     * acceptable if there is no cube overlap, and the whole Puzzle is contained in a
     * 3x3x3 cube.
     * 
     * @return {@code true} if this {@code Puzzle} is in an acceptable state.
     */
    public boolean isCorrect() {
        // check if this Puzzle is contained in a 3x3 cube
        for (int i = 0; i < max.length; i++) {
            if (max[i] - min[i] > 2) {
                System.out.println("   Overshoot - axis " + i + ": [" + min[i] + ", " + max[i]
                        + "]");
                return false;
            }
        }
        // check for overlaps
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (matrix[i][j][k] > 1) {
                        System.out.println("   Overlap in (" + i + ", " + j + ", " + k + ")");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns whether this {@code Puzzle} has reached its full size.
     * 
     * @return {@code true} if all cubes has been added to this {@code Puzzle}.
     */
    public boolean isFull() {
        return size() == LinksList.getInstance().size();
    }

    /**
     * Returns a list of the possible cubes to add to this {@code Puzzle}, depending
     * on its current state. The returned cubes are the possibilities respecting this
     * {@code Puzzle}'s link constraints.<br>
     * This does not deal with overlaps and overshoots of the {@code Puzzle}'s
     * bounds. In other words, if a Cube from this list is added,
     * {@link #isCorrect()} might return {@code false}.
     * 
     * @return A list of the possible cubes to add to this {@code Puzzle}.
     */
    public List<Cube> getNextPossibleCubes() {
        return LinksList.getInstance().get(size() - 1)
                .getNextPossibleCubes(get(size() - 2), get(size() - 1));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cube c : this) {
            sb.append(c.toString());
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
