package basic.sudoku;

public class Puzzle {
    /** Class Attributes */
    private int[][] grid;



    /** Public methods */

    /**
     * Create Puzzle instance
     * @param sudokuGrid - int[9][9] with zeros in place of empty cells.
     * @throws Error - If the grid is not valid.
     */
    public Puzzle(int[][] sudokuGrid) throws Error {

        // Check validity of puzzle.
        if (sudokuGrid.length != 9) throw new Error("Wrong number of rows.");
        for (int i = 0; i < 9; i++) {
            int[] is = sudokuGrid[i];
            if (is.length != 9) throw new Error("Row does not have 9 cells.");
        }

        // Copy grid by value
        grid = new int[9][9];
        for (int r = 0; r < 9; r++) {
            grid[r] = sudokuGrid[r].clone();
        }
    }


    /**
     * Grid representation of puzzle.
     * @return int[9][9] with zeros in place of empty cells.
     */
    public int[][] getGrid() {
        int[][] outGrid = new int[9][9];

        for (int r = 0; r < 9; r++) {
            outGrid[r] = grid[r].clone();
        }

        return outGrid;
    }


    /**
     * Return a copy of self.
     * @return new puzzle with identical grid.
     */
    public Puzzle copy() {
        int[][] newGrid = getGrid();

        return new Puzzle(newGrid);
    }


    /**
     * Get the r'th row
     * @param r - which row, indexed from 0
     * @return int[9] - the row.
     */
    public int[] getRow(int r) {
        return grid[r];
    }


    /**
     * Get the c'th column
     * @param c - which column, indexed from 0
     * @return int[9] - the column
     */
    public int[] getCol(int c) {
        int[] col = new int[9];
        for (int r = 0; r < 9; r++) {
            col[r] = grid[r][c];
        }

        return col;
    }


    /**
     * Get the 3x3 box containing the (r,c) cell.
     * @param r - row of cell in question
     * @param c - column of cell
     * @return int[3][3] - the box.
     */
    public int[][] getBox(int r, int c) {
        int[][] box = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box[i][j] = grid[r - (r%3) + i][c - (c%3) + j];
            }
        }

        return box;
    }


    /**
     * Check if the puzzle is a valid sudoku
     * @return - True if rows, columns, and boxes contain no repeat numbers.
     */
    public boolean checkIsValid(boolean print) {

        for (int i = 0; i < 9; i++) {
            if ( !rowIsValid(i, print) ) return false;
            if ( !colIsValid(i, print) ) return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ( !boxIsValid(3*i, 3*j, print) ) return false;
            }
        }

        return true;
    }


    public boolean checkIsValid() {
        return checkIsValid(false);
    }


    /**
     * Check if the puzzle is solved
     * @return - true if valid and contains no empty cells.
     */
    public boolean checkIsSolved(boolean print) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) return false;
            }
        }

        return checkIsValid(print);
    }


    public boolean checkIsSolved() {
        return checkIsSolved(false);
    }


    /** Print string representation */
    public void print() {
        System.out.println(getStr());
    }


    /**
     * Pretty formatted string.
     * @return - String representation of puzzle
     */
    public String getStr() {
        String str =  "+=================+\n";
        for (int r = 0; r < 9; r++) {
            str += "‖";
            for (int c = 0; c < 9; c++) {
                String cell;

                if (grid[r][c] == 0) {
                    cell = " ";
                } else {
                    cell = Integer.toString(grid[r][c]);
                }

                if (c < 8) {
                    if (c % 3 == 2) cell += "‖";
                    else cell += "|";
                } else {
                    cell += "‖\n";
                }

                str += cell;
            }

            if (r < 8) {
                if (r % 3 == 2) str += "+=================+\n";
                else str += "+-----------------+\n";
            } else {
                str +=  "+=================+";
            }
        }

        return str;
    }



    /** Private Methods */

    /**
     * Check row is valid
     * @param r - row number, indexed from 0
     * @param print - whether to print QA statements
     * @return true if the row does not contain repeat values.
     */
    private boolean rowIsValid(int r, boolean print) {
        int[] row = getRow(r);

        boolean isValid = setIsValid(row);
        if (!isValid && print) System.out.println("Row " + r + " is not valid.");

        return isValid;
    }


    /**
     * Check column is valid
     * @param c - column number, indexed from 0
     * @param print - whether to print QA statements
     * @return - true if the column has no repeat elements
     */
    private boolean colIsValid(int c, boolean print) {
        int[] col = getCol(c);

        boolean isValid = setIsValid(col);
        if (!isValid && print) System.out.println("Col " + c + " is not valid.");

        return isValid;
    }


    /**
     * Check box is valid
     * @param r - row of a cell in the box
     * @param c - col of a cell in the box
     * @param print - whether to print QA statements
     * @return True if the box has no repeat elements
     */
    private boolean boxIsValid(int r, int c, boolean print) {
        // Index box with top-left index
        int[][] box = getBox(r, c);

        int[] boxFlat = new int[9];

        // Unroll box into array.
        for (int k = 0; k < 9; k++) {
            boxFlat[k] = box[k/3][k%3];
        }

        boolean isValid = setIsValid(boxFlat);
        if (!isValid && print) System.out.println("Box containing (" + r + "," + c + ") is not valid.");

        return isValid;
    }


    /**
     * Check set of cells is valid
     * @param set - List of 9 cells, in no particular order
     * @return true if the list contains no repeat cells.
     */
    private boolean setIsValid(int[] set) {
        // I'm learning java and do not know if new int[9] initializes with zeros.
        int[] mem = {0,0,0,0,0,0,0,0,0};

        for (Integer v : set) {
            if (v != 0) {
                mem[v-1]++;
                if (mem[v-1] > 1)  return false;
            }
        }

        return true;
    }

}
