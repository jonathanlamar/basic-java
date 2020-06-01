package basic.sudoku;

public class GuessAndState {
    public int row;
    public int column;
    public int value;
    public Puzzle puzzle;

    public GuessAndState(Puzzle puz, int r, int c, int val) throws Error {

        if (puz.getGrid()[r][c] != 0) throw new Error("Occupied position.");

        puzzle = puz.copy();
        row = r;
        column = c;
        value = val;

        if (!execute().checkIsValid()) throw new Error("Can't make this guess.");
    }


    public Puzzle execute() {
        int[][] grid = puzzle.getGrid();
        grid[row][column] = value;

        return new Puzzle(grid);
    }
}

