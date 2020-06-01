package basic.sudoku;

public class Position {
    public int r;
    public int c;

    public Position(int row, int col) {
        r = row;
        c = col;
    }

    public String getKey() {
        return Integer.toString(r) + Integer.toString(c);
    }
}
