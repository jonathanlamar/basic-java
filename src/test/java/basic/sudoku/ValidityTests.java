package basic.sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class ValidityTests {
    @Test
    public void testGenerateAlwaysValid() throws IOException {
        // I believe 10 will convice me.
        for (int i = 0; i < 10; i++) {
            Puzzle puz = Generator.getNewPuzzle();
            assertTrue(puz.checkIsValid(true));
        }
    }


    @Test
    public void testGenerateAlwaysUnsolved() throws IOException {
        // I believe 10 will convice me.
        for (int i = 0; i < 10; i++) {
            Puzzle puz = Generator.getNewPuzzle();
            assertFalse(puz.checkIsSolved(true));
        }
    }


    @Test
    public void testCheckIsValid() throws FileNotFoundException, IOException {

        Puzzle validPuz = Parser.readFromCsv("src/test/resources/valid.csv");
        assertTrue(validPuz.checkIsValid(true));

        // This has a repeat element in one row
        Puzzle rowInvalidPuz = Parser.readFromCsv("src/test/resources/invalid_row.csv");
        assertFalse(rowInvalidPuz.checkIsValid(true));

        // This has a repeat element in one col
        Puzzle colInvalidPuz = Parser.readFromCsv("src/test/resources/invalid_col.csv");
        assertFalse(colInvalidPuz.checkIsValid(true));

        // This has a repeat element in one box
        Puzzle boxInvalidPuz = Parser.readFromCsv("src/test/resources/invalid_box.csv");
        assertFalse(boxInvalidPuz.checkIsValid(true));

    }
}
