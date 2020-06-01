package basic.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solver {

    /**
     * Solve the puzzle
     * @param puz - unsolved puzzle
     * @return Puzzle, solved
     */
    public static Puzzle solvePuzzle(Puzzle puz) throws Error {

        if ( puz.checkIsSolved() ) return puz;

        // We will implement a backtracking method, holding previous guesses in a stack
        Stack<GuessAndState> prevGuesses = new Stack<GuessAndState>();
        // Get all valid numbers to try.
        prevGuesses.addAll(getNextGuesses(puz));

        while (!prevGuesses.isEmpty()) {
            GuessAndState guess = prevGuesses.pop();
            
            System.out.println(String.format(
                "Popping guess %d at (%d,%d) off the stack.",
                guess.value, guess.row, guess.column)
            );

            puz = guess.execute();

            System.out.println("Current state:");
            System.out.println(puz.getStr());

            if (!puz.checkIsValid()) {
                System.out.println(puz.getStr());
                throw new Error("Invalid path.");
            }

            if (puz.checkIsSolved()) break;

            // Get all valid numbers to try.
            List<GuessAndState> nextGuesses = getNextGuesses(puz);
            prevGuesses.addAll(nextGuesses);
        }

        if (!puz.checkIsValid()) System.out.println("Final puzzle is not valid.");
        if (!puz.checkIsSolved()) System.out.println("Final puzzle is not solved.");
        return puz;
    }


    /**
     * Get all guesses at all positions, except previous bad guesses.
     * @param puz - Puzzle being solved
     * @return - All guesses
     * @throws Error - If any position lacks any guesses.
     */
    public static List<GuessAndState> getNextGuesses(Puzzle puz) throws Error {
        List<GuessAndState> guesses = new ArrayList<GuessAndState>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (puz.getGrid()[r][c] != 0) continue;

                List<GuessAndState> guessesAtPos = getGuessesAtPos(puz, r, c);

                // If any position has no valid guesses, we are in a bad path.
                if (guessesAtPos.isEmpty()) return new ArrayList<GuessAndState>();

                guesses.addAll(guessesAtPos);
            }
        }

        return guesses;
    }


    /**
     * Get all guesses at the given position
     * @param puz - Puzzle being solved
     * @param r - row
     * @param c - column
     * @return - All guesses
     * @throws Error - If the position lacks any guesses.
     */
    public static List<GuessAndState> getGuessesAtPos(Puzzle puz, int r, int c) throws Error {

        if (puz.getGrid()[r][c] != 0) throw new Error("Making a guess on a filled cell.");

        int[] row = puz.getRow(r);
        int[] col = puz.getCol(c);
        int[][] box = puz.getBox(r, c);

        // Eliminate possible guesses
        boolean[] canGuess = {true,true,true,true,true,true,true,true,true};

        System.out.println(String.format( "Forming guesses for (%d,%d).", r, c ));

        for (int i = 0; i < 9; i++) {
            int rowVal = row[i];
            if (rowVal != 0) {
                canGuess[rowVal-1] = false;
                System.out.println(String.format("Nulling out %d", rowVal));
            }

            int colVal = col[i];
            if (colVal != 0) {
                canGuess[colVal-1] = false;
                System.out.println(String.format("Nulling out %d", colVal));
            }

            int boxVal = box[i/3][i%3];
            if (boxVal != 0) {
                canGuess[boxVal-1] = false;
                System.out.println(String.format("Nulling out %d", boxVal));
            }
        }

        // What remains is the guesses list
        List<GuessAndState> guesses = new ArrayList<GuessAndState>();

        for (int i = 0; i < 9; i++) {
            if (canGuess[i]) {
                System.out.println(String.format("Pushing guess %d at (%d,%d) onto stack.", i+1, r, c));
                GuessAndState guess = new GuessAndState(puz, r, c, i+1);
                guesses.add(guess);
            }
        }

        if (guesses.isEmpty()) System.out.println(String.format("Empty guess set at (%d,%d).", r, c));

        return guesses;
    }
}
