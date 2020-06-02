package basic.sudoku;

import java.util.HashMap;
import java.util.Stack;

public class Solver {

    /**
     * Solve the puzzle
     * @param puz - unsolved puzzle
     * @return Puzzle, solved
     */
    public static Puzzle solvePuzzle(Puzzle puz, boolean print) throws Error, InterruptedException {

        // This is the main check for invalid puzzles.
        if (!puz.checkIsValid()) throw new Error("Puzzle is not valid.");

        // Some of the code that follows assumes an unsolved puzzle.
        if (puz.checkIsSolved()) return puz;

        // clear screen and show puzzle.
        if (print) printAndClear(puz);

        // We will implement a backtracking method, holding previous guesses in a stack
        HashMap<String, Stack<Integer>> prevGuesses = new HashMap<String, Stack<Integer>>();

        // Initalize previous guesses.
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                prevGuesses.put(getKey(r, c), new Stack<Integer>());
            }
        }

        // Position of first empty cell
        int[] nextPos = getNextEmptyCell(puz);

        // All guesses at that cell.
        Stack<GuessAndState> guessesAtPos = getGuessesAtPos(puz, nextPos[0], nextPos[1]);

        while (!puz.checkIsSolved() && !guessesAtPos.empty()) {

            GuessAndState guess = guessesAtPos.pop();

            try {
                puz = solvePuzzle(guess.execute(), print);
            } catch (Error e) {
                System.out.println(e.getLocalizedMessage());
                continue;
            }
        }

        if (!puz.checkIsSolved()) throw new Error("Puzzle is not solved.");

        return puz;
    }


    public static Puzzle solvePuzzle(Puzzle puz) throws Error, InterruptedException {
        return solvePuzzle(puz, false);
    }


    public static void printAndClear(Puzzle puzzle) throws InterruptedException {
        // Clear screen
        System.out.print("\033[H\033[2J");  
        System.out.flush();  

        System.out.println("Here is the puzzle:");
        puzzle.print();

        // Wait 0.1 seconds
        Thread.sleep(100);
    }

    public static String getKey(int row, int column) {
        return Integer.toString(row) + Integer.toString(column);
    }

    public static int[] getNextEmptyCell(Puzzle puz) {
        int[][] grid = puz.getGrid();

        // Find lexicographically first empty cell.
        int guessRow = 0;
        int guessCol = 0;
        while (grid[guessRow][guessCol] != 0) {
            if (guessCol < 8) guessCol++;
            else if (guessRow < 8) {
                guessCol = 0;
                guessRow++;
            } else break;
        }

        if (guessCol == 8 && guessRow == 8) {
            System.out.println("Error - no empty cells.  Current state:");
            System.out.println(puz.getStr());
            throw new Error("No empty cells.");
        }

        return new int[] {guessRow, guessCol};
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

        System.out.println(String.format( "Forming guesses for (%d,%d).", r, c ));

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
                System.out.println(String.format("Pushing guess %d at (%d,%d) onto stack.", i+1, r, c));
                GuessAndState guess = new GuessAndState(puz, r, c, i+1);
                guesses.push(guess);
            }
        }

        if (guesses.isEmpty()) System.out.println(String.format("Empty guess set at (%d,%d).", r, c));

        return guesses;
    }
}
