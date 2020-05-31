package basic.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        List<Integer> set = new ArrayList<Integer>();

        for (int c = 0; c < 9; c++) {
            set.add(grid[r][c]);
        }

        return setIsValid(set);
    }

    public boolean colIsValid(int c) {
        List<Integer> set = new ArrayList<Integer>();

        for (int r = 0; r < 9; r++) {
            set.add(grid[r][c]);
        }

        return setIsValid(set);
    }

    public boolean boxIsValid(int i, int j) {
        List<Integer> set = new ArrayList<Integer>();

        // Unroll box into array.
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                set.add(grid[3*i + r][3*j + c]);
            }
        }

        return setIsValid(set);
    }

    public boolean setIsValid(List<Integer> set) {
        int[] mem = {0,0,0,0,0,0,0,0,0};

        for (Integer v : set) {
            if (v != -1) {
                mem[v-1]++;
                if (mem[v-1] > 1)  return false;
            }
        }

        return true;
    }

    public void setAtPosition(PositionAndVal positionAndVal) {
        grid[positionAndVal.r][positionAndVal.c] = positionAndVal.val;
    }

    public void solve() {
        Stack<PositionAndVal> entryStack = new Stack<PositionAndVal>();

        entryStack = solveIter(entryStack);

        while (!entryStack.isEmpty()) {
            setAtPosition(entryStack.pop());
        }
    }

    public Stack<PositionAndVal> solveIter(Stack<PositionAndVal> guesses) {
        int[] pos = getPos();

        if (pos[0] != -1 && pos[1] != -1) {
            List<Integer> vals = getPossibleVals(pos[0], pos[1]);

            for (int val : vals) {
                PositionAndVal posVal = new PositionAndVal(pos[0], pos[1], val);
                guesses.push(posVal);
                solveIter(guesses);
            }
        }

        return guesses;
    }

    public List<Integer> getPossibleVals(int row, int col) {
        boolean[] isPossible = {true,true,true,true,true,true,true,true,true};

        for (int c = 0; c < 9; c++) {
            if (grid[row][c] != -1) isPossible[grid[row][c] - 1] = false;
        }

        for (int r = 0; r < 9; r++) {
            if (grid[r][col] != -1) isPossible[grid[r][col] - 1] = false;
        }

        int quadR = row / 3;
        int quadC = col / 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                int quadVal = grid[quadR + r][quadC + c];

                if (quadVal != -1) isPossible[quadVal - 1] = false;
            }
        }

        List<Integer> possibleVals = new ArrayList<Integer>();

        for (int i = 0; i < 9; i++) {
            if (isPossible[i]) possibleVals.add(i+1);
        }

        return possibleVals;
    }

    public int[] getPos() {
        int[] out = {-1, -1};

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == -1) {
                    out[0] = r;
                    out[1] = c;
                }
            }
        }

        return out;
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
