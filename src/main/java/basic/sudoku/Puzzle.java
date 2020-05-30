package basic.sudoku;

public class Puzzle {
    int[][] grid;

    public Puzzle(int[][] sudokuGrid) throws Error {

        // Check validity of puzzle.
        if (sudokuGrid.length != 9) throw new Error("Wrong number of rows.");
        for (int i = 0; i < 9; i++) {
            int[] is = sudokuGrid[i];
            if (is.length != 9) throw new Error("Row does not have 9 cells.");
        }

        grid = sudokuGrid; 
    }

    public boolean checkIsValid() {

        for (int i = 0; i < 9; i++) {
            if ( !rowIsValid(i) ) return false;
            if ( !colIsValid(i) ) return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ( !boxIsValid(i, j) ) return false;
            }
        }

        return true;
    }

    public boolean rowIsValid(int r) {
        int[] set = grid[r];

        return setIsValid(set);
    }

    public boolean colIsValid(int c) {
        int[] set = new int[9];

        for (int i = 0; i < 9; i++) {
            set[i] = grid[i][c];
        }

        return setIsValid(set);
    }

    public boolean boxIsValid(int i, int j) {
        int[] set = new int[9];

        // Unroll box into array.
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                set[3*r+c] = grid[3*i + r][3*j + c];
            }
        }

        return setIsValid(set);
    }

    private boolean setIsValid(int[] set) {
        int[] mem = new int[9];

        for (int i = 0; i < 9; i++) {
            int v = set[i];
            if (v != -1) {
                mem[v-1]++;
                if (mem[v-1] > 1)  return false;
            }
        }

        return true;
    }

    public Puzzle solve() {
        return this;
    }

    public String getStr() {
        String str =  "+=================+\n";
        for (int r = 0; r < 9; r++) {
            str += "‖";
            for (int c = 0; c < 9; c++) {
                String cell;

                if (grid[r][c] == -1) {
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
}
