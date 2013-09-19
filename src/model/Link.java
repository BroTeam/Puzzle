package model;

import java.util.LinkedList;

/**
 * A {@code Link} describes the way 3 {@link Cube}s follow each other.
 * <p>
 * When comparing the {@code Cube}s list and the {@link LinksList}, a {@code Link} has
 * the same index as the central {@code Cube} of the 3 that it describes. This
 * central {@code Cube} is referred to as the "associated" {@code Cube}.
 * </p>
 * <p>
 * This enum's values mean:
 * <ul>
 * <li>{@link #END}: the associated cube is an end cube.</li>
 * <li>{@link #STRAIGHT}: the associated cube is in the middle of 3 cubes following
 * each other in a straight line. This can be represented as:<br>
 * 
 * <pre>
 * {@code OOO}
 * </pre>
 * 
 * </li>
 * <li>{@link #ELBOW}: the associated cube is in the middle of an elbow, which means
 * that the 3 cubes form a 90-degrees angle. This can be represented as:<br>
 * 
 * <pre>
 * {@code OO
 * O}
 * </pre>
 * 
 * </li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:joffrey.bion@gmail.com">Joffrey Bion</a>
 */
enum Link {
    END {
        @Override
        LinkedList<Cube> getNextPossibleCubes(Cube previous, Cube current) {
            throw new RuntimeException("Cannot call this method on an end link.");
        }
    },
    STRAIGHT {
        @Override
        LinkedList<Cube> getNextPossibleCubes(Cube previous, Cube current) {
            LinkedList<Cube> cubes = new LinkedList<>();
            int[] direction = previous.directionTo(current);
            cubes.add(new Cube(current).add(direction[0], direction[1]));
            return cubes;
        }
    },
    ELBOW {
        @Override
        LinkedList<Cube> getNextPossibleCubes(Cube previous, Cube current) {
            LinkedList<Cube> cubes = new LinkedList<>();
            LinkedList<Integer> possibleAxes = new LinkedList<>();
            possibleAxes.add(0);
            possibleAxes.add(1);
            possibleAxes.add(2);
            int[] direction = previous.directionTo(current);
            possibleAxes.remove(direction[0]);
            for (int axis : possibleAxes) {
                cubes.add(new Cube(current).add(axis, 1));
                cubes.add(new Cube(current).add(axis, -1));
            }
            return cubes;
        }
    };

    /**
     * Returns a list of the possible cubes that can follow the 2 specified cubes
     * with this {@code Link}.
     * 
     * @param previous
     *            The position of the previous {@code Cube}, which is the first among
     *            the 3 considered cubes.
     * @param current
     *            The position of the associated {@code Cube}, which is the second
     *            among the 3 considered cubes.
     * @return A list of the possible cubes for the 3rd position of this {@code Link}
     *         .
     */
    abstract LinkedList<Cube> getNextPossibleCubes(Cube previous, Cube current);
}
