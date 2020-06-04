package basic.sudoku;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class SolverTests {
    @Test
    public void testSolutionExists() throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Puzzle puz = Generator.getNewPuzzle();
            Puzzle solvedPuz = Solver.solvePuzzle(puz);

            assertTrue(solvedPuz.checkIsValid());
            assertTrue(solvedPuz.checkIsSolved());
        }
    }

    @Test
    public void testSolutionUnique() {
        // TODO: Solve 10 puzzles, record them to compare.
    }
}
