package basic.sudoku;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class GeneratorTests {
    @Test
    public void testValidPuzzle() throws IOException {
        Generator gen = new Generator();

        // I believe 10 will convice me.
        for (int i = 0; i < 10; i++) {
            Puzzle puz = gen.getNewPuzzle();
            assertTrue(puz.checkIsValid());
        }
    }
}
