package basic.sudoku;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Solver {

    /**
     * Solve the puzzle
     * @param puz - unsolved puzzle
     * @return Puzzle, solved
     */
    public static Puzzle solvePuzzle(Puzzle puz) throws EmptyStackException, Error {

        // We will implement a backtracking method, holding previous guesses in a stack
        Stack<PositionAndVal> prevGuesses = new Stack<PositionAndVal>();

        // Keep track of bad guesses
        HashMap<String, Stack<Integer>> badGuesses = new HashMap<String, Stack<Integer>>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String key = (new Position(r, c)).getKey();
                badGuesses.put(key, new Stack<Integer>());
            }
        }

        while ( !puz.checkIsSolved() ) {

            if (!puz.checkIsValid()) throw new Error("Puzzle is not valid!");

            // Get all valid numbers to try.
            List<PositionAndVal> nextGuesses = getNextGuesses(puz, badGuesses);

            System.out.println("Making new list of guesses.  It has " + nextGuesses.size() + " elements.");

            // If no guesses, pop and continue..?
            if (nextGuesses.size() == 0) {
                PositionAndVal prevGuess = prevGuesses.pop();
                System.out.println(
                    "Deleting last guess of " + prevGuess.val + " at (" 
                    + prevGuess.pos.r + "," + prevGuess.pos.c + ")."
                );
                puz = deleteGuess(puz, prevGuess);

                String key = prevGuess.getKey();
                Stack<Integer> badGuessesAtPos = badGuesses.get(key);
                badGuessesAtPos.push(prevGuess.val);
                badGuesses.put(key, badGuessesAtPos); // Is this necessary?

                continue;
            }

            // Take first guess and insert.
            PositionAndVal nextGuess = nextGuesses.get(0);
            System.out.println(
                "Making guess of " + nextGuess.val + " at (" 
                + nextGuess.pos.r + "," + nextGuess.pos.c + ")."
            );
            puz = makeGuess(puz, nextGuess);
            prevGuesses.push(nextGuess);
        }

        return puz;
    }


    /**
     * Get all guesses at all positions, except previous bad guesses.
     * @param puz - Puzzle being solved
     * @param badGuesses - Previous bad guesses
     * @return - All guesses
     */
    public static List<PositionAndVal> getNextGuesses(Puzzle puz, HashMap<String, Stack<Integer>> badGuesses) {
        int[][] grid = puz.getGrid();
        List<PositionAndVal> guesses = new ArrayList<PositionAndVal>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    Position pos = new Position(r, c);
                    String key = pos.getKey();
                    List<Integer> guessesAtPos = getGuessesAtPos(puz, pos, badGuesses.get(key));
                    
                    for (int v : guessesAtPos) {
                        guesses.add(new PositionAndVal(r, c, v));
                    }
                }
            }
        }

        return guesses;
    }


    /**
     * Make guess in puzzle
     * @param puz - Puzzle being solved
     * @param guess - guess to make
     * @return - puz with guess filled in
     * @throws Error - throws if the space is occupied.
     */
    public static Puzzle makeGuess(Puzzle puz, PositionAndVal guess) throws Error {
        int[][] grid = puz.getGrid();
        int obsVal = grid[guess.pos.r][guess.pos.c];

        if (obsVal != 0) throw new Error("pos is filled.");
        
        grid[guess.pos.r][guess.pos.c] = guess.val;

        return new Puzzle(grid);
    }


    /**
     * Delete guess from Puzzle instance.
     * @param puz - Puzzle being solved
     * @param guess - guess to delete
     * @return - puz with guess deleted
     * @throws Error - throws if actual value at pos does not match guess.
     */
    public static Puzzle deleteGuess(Puzzle puz, PositionAndVal guess) throws Error {
        int[][] grid = puz.getGrid();
        int obsVal = grid[guess.pos.r][guess.pos.c];

        if (obsVal == 0) throw new Error("No guess at pos.");
        else if (obsVal != guess.val) throw new Error("Guess does not match observed value.");
        
        grid[guess.pos.r][guess.pos.c] = 0;

        return new Puzzle(grid);
    }


    /**
     * Get all guesses at given position, except previous bad guesses
     * @param puz - Puzzle being solved
     * @param pos - Position being considered
     * @param badGuesses - previous bad guesses
     * @return - all guesses at pos
     */
    public static List<Integer> getGuessesAtPos(Puzzle puz, Position pos, Stack<Integer> badGuesses) {
        int[] row = puz.getRow(pos.r);
        int[] col = puz.getCol(pos.c);
        int[][] box = puz.getBox(pos.r, pos.c);

        // Eliminate possible guesses
        boolean[] canGuess = {true,true,true,true,true,true,true,true,true};

        for (int i = 0; i < 9; i++) {
            int rowVal = row[i];
            if (rowVal != 0) canGuess[rowVal-1] = false;

            int colVal = col[i];
            if (colVal != 0) canGuess[colVal-1] = false;

            int boxVal = box[i/3][i%3];
            if (boxVal != 0) canGuess[boxVal-1] = false;
        }

        // Eliminate previous bad guesses
        for (int prevGuess : badGuesses) {
            canGuess[prevGuess-1] = false;
        }

        // What remains is the guesses list
        List<Integer> guesses = new ArrayList<Integer>();

        for (int i = 0; i < 9; i++) {
            if (canGuess[i]) guesses.add(i+1);
        }

        return guesses;
    }
}
