package basic.sudoku;

public class GuessAndState {
    public int row;
    public int column;
    public int value;
    private Puzzle puzzle;

    /**
     * Holds content of a Puzzle instance, location of an empty cell, and the
     * putative value for that cell.
     * @param puz - Puzzle to be guessed.
     * @param r - row of guess
     * @param c - col of guess
     * @param val - val to guess
     * @throws Error - if the location of the guess is filled.
     */
    public GuessAndState(Puzzle puz, int r, int c, int val) throws Error {

        if (puz.getGrid()[r][c] != 0) throw new Error("Occupied position.");

        puzzle = puz.copy();
        row = r;
        column = c;
        value = val;

        if (!execute().checkIsValid()) throw new Error("Can't make this guess.");
    }


    /**
     * Make the guess.
     * @return - Puzzle with guess filled in
     */
    public Puzzle execute() {
        int[][] grid = puzzle.getGrid();
        grid[row][column] = value;

        return new Puzzle(grid);
    }


    /**
     * Return Puzzle, prior to guessing.
     * @return
     */
    public Puzzle getPuzzle() {
        return puzzle.copy();
    }
}

