package basic.sudoku;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class PuzzleTests {
    @Test
    public void testRowIsValid() throws FileNotFoundException, IOException {

        // This has a repeat element in one row
        Puzzle rowInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_row.csv");

        for (int r = 0; r < 9; r++) {
            if (r == 5) {
                assertEquals(false, rowInvalidPuz.rowIsValid(r));
            } else {
                assertEquals(true, rowInvalidPuz.rowIsValid(r));
            }
        }
    }

    @Test
    public void testColIsValid() throws FileNotFoundException, IOException {

        // This has a repeat element in one col
        Puzzle colInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_col.csv");

        for (int c = 0; c < 9; c++) {
            if (c == 1) {
                assertEquals(false, colInvalidPuz.colIsValid(c));
            } else {
                assertEquals(true, colInvalidPuz.colIsValid(c));
            }
        }
    }

    @Test
    public void testBoxIsValid() throws FileNotFoundException, IOException {

        // This has a repeat element in one box
        Puzzle boxInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_box.csv");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 2 && c == 0) {
                    assertEquals(false, boxInvalidPuz.boxIsValid(r, c));
                } else {
                    assertEquals(true, boxInvalidPuz.boxIsValid(r, c));
                }
            }
        }
    }

    @Test
    public void testCheckIsValid() throws FileNotFoundException, IOException {

        Puzzle validPuz = Parser.readFromCsv("src/test/resources/sudoku_example.csv");
        assertEquals(true, validPuz.checkIsValid());

        // This has a repeat element in one row
        Puzzle rowInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_row.csv");
        assertEquals(false, rowInvalidPuz.checkIsValid());

        // This has a repeat element in one col
        Puzzle colInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_col.csv");
        assertEquals(false, colInvalidPuz.checkIsValid());

        // This has a repeat element in one box
        Puzzle boxInvalidPuz = Parser
            .readFromCsv("src/test/resources/sudoku_example_invalid_box.csv");
        assertEquals(false, boxInvalidPuz.checkIsValid());

    }
}
