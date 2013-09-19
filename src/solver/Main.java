package solver;

import java.util.LinkedList;

import model.Cube;
import model.Puzzle;

/**
 * The main class of the solver.
 * 
 * @author <a href="mailto:joffrey.bion@gmail.com">Joffrey Bion</a>
 */
public class Main {

    /**
     * The entry point of the program to solve the puzzle.
     * 
     * @param args
     *            Unused command-line arguments.
     */
    public static void main(String[] args) {
        printFirstSolution();
        //printAllSolutions();
        System.out.println("All the solutions appear to be the 4 rotations of the first\n"
                + "solution, and each of their symmetric. We can therefore consider\n"
                + "there is only one solution to the problem.");
    }

    /**
     * Prints the first solution to the puzzle.
     */
    private static void printFirstSolution() {
        Puzzle puzzle = new Puzzle();
        if (backtrackFirst(puzzle)) {
            System.out.println();
            System.out.println("Solution found:");
            System.out.println(puzzle);
        } else {
            System.out.println();
            System.out.println("No solution found.");
        }
    }

    /**
     * Prints all the solutions to the puzzle.
     */
    @SuppressWarnings("unused")
    private static void printAllSolutions() {
        LinkedList<Puzzle> solutions = new LinkedList<>();
        backtrack(new Puzzle(), solutions);
        System.out.println();
        int i = 1;
        for (Puzzle sol : solutions) {
            System.out.println("Solution " + i++ + ":");
            System.out.println(sol);
        }
    }

    /**
     * Tries all possibilities to add cubes to the specified puzzle, and returns
     * whether a solution has been found. If no solution has been found, then the
     * specified puzzle is unchanged. If a solution has been found, then the puzzle
     * is full and contains the solution.
     * 
     * @param puzzle
     *            A puzzle which may or may not be full.
     * @return {@code true} if an acceptable solution has been found.
     */
    private static boolean backtrackFirst(Puzzle puzzle) {
        if (!puzzle.isCorrect()) {
            return false;
        }
        if (puzzle.isFull() && puzzle.isCorrect()) {
            return true;
        }
        for (Cube c : puzzle.getNextPossibleCubes()) {
            System.out.println("Trying " + c);
            puzzle.add(c);
            if (backtrackFirst(puzzle)) {
                return true;
            }
            puzzle.remove(c);
            System.out.println("Removed " + c);
        }
        return false;
    }

    /**
     * Tries all possibilities to add cubes to the specified puzzle. The specified
     * puzzle is always unchanged. If a solution has been found, it is added to the
     * specified list of solutions.
     * 
     * @param puzzle
     *            A puzzle which may or may not be full.
     * @param solutions
     *            A list of the solutions found in the process.
     */
    private static void backtrack(Puzzle puzzle, LinkedList<Puzzle> solutions) {
        if (!puzzle.isCorrect()) {
            return;
        }
        if (puzzle.isFull() && puzzle.isCorrect()) {
            solutions.add(new Puzzle(puzzle));
            return;
        }
        for (Cube c : puzzle.getNextPossibleCubes()) {
            System.out.println("Trying " + c);
            puzzle.add(c);
            backtrack(puzzle, solutions);
            puzzle.remove(c);
            System.out.println("Removed " + c);
        }
    }

}
