package basic.sudoku;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
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
    public void testSolutionUnique() throws IOException, FileNotFoundException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Puzzle unsolvedPuz = Parser.readFromCsv(
                String.format("src/test/resources/unsolved%d.csv", i));

            Puzzle solvedPuz1 = Solver.solvePuzzle(unsolvedPuz);
            Puzzle solvedPuz2 = Parser.readFromCsv(
                String.format("src/test/resources/solved%d.csv", i));

            int[][] grid1 = solvedPuz1.getGrid();
            int[][] grid2 = solvedPuz2.getGrid();

            assertTrue(gridsAreEqual(grid1, grid2));
        }
    }

    private boolean gridsAreEqual(int[][] grid1, int[][] grid2) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid1[r][c] != grid2[r][c]) return false;
            }
        }

        return true;
    }
}
