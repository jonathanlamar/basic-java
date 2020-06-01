package basic.sudoku;

public class PositionAndVal {
    public Position pos;
    public int val;

    public PositionAndVal(int row, int col, int value) {
        pos = new Position(row, col);
        val = value;
    }

    public String getKey() {
        return pos.getKey();
    }
}
