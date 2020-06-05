package basic.sudoku;

import java.util.Stack;

public class Solver {

    /**
     * Solve the puzzle using a backtracking algorithm.
     * @param puz - unsolved puzzle
     * @param print - whether to show an animation of the algorithm
     * @return Puzzle, solved
     */
    public static Puzzle solvePuzzle(Puzzle puz, boolean print) throws Error, InterruptedException {

        if (!puz.checkIsValid()) throw new Error("Puzzle is not valid.");

        if (puz.checkIsSolved()) return puz;

        if (print) puz.printAndClear();

        int[] nextPos = puz.getNextEmptyCell();

        // We will implement a backtracking method, holding previous guesses in a stack
        Stack<GuessAndState> guessesAtPos = getGuessesAtPos(puz, nextPos[0], nextPos[1]);

        while (!puz.checkIsSolved() && !guessesAtPos.empty()) {

            GuessAndState guess = guessesAtPos.pop();

            try {
                puz = solvePuzzle(guess.execute(), print);
            } catch (Error e) {
                // System.out.println(e.getLocalizedMessage());
                continue;
            }
        }

        if (!puz.checkIsSolved()) throw new Error("Puzzle is not solved.");

        return puz;
    }


    /**
     * Solve the puzzle using a backtracking algorithm.
     * @param puz - unsolved puzzle
     * @return Puzzle, solved
     */
    public static Puzzle solvePuzzle(Puzzle puz) throws Error, InterruptedException {
        return solvePuzzle(puz, false);
    }


    /**
     * Get all guesses at the given position
     * @param puz - Puzzle being solved
     * @param r - row
     * @param c - column
     * @return - All guesses
     * @throws Error - If the position lacks any guesses.
     */
    public static Stack<GuessAndState> getGuessesAtPos(Puzzle puz, int r, int c) throws Error {

        if (puz.getGrid()[r][c] != 0) throw new Error("Making a guess on a filled cell.");

        int[] row = puz.getRow(r);
        int[] col = puz.getCol(c);
        int[][] box = puz.getBox(r, c);

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

        // What remains is the guesses list
        Stack<GuessAndState> guesses = new Stack<GuessAndState>();

        for (int i = 0; i < 9; i++) {
            if (canGuess[i]) {
                GuessAndState guess = new GuessAndState(puz, r, c, i+1);
                guesses.push(guess);
            }
        }

        return guesses;
    }
}
