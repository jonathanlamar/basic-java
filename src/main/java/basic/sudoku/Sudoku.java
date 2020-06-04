package basic.sudoku;

import java.io.IOException;

public class Sudoku {
    public static void main(String[] args) throws IOException, InterruptedException {

        promptEnterKey("Press Enter to grab a sudoku puzzle."); 

        System.out.println("Here is your puzzle.");
        Puzzle puz = Generator.getNewPuzzle(true);

        promptEnterKey("Press Enter to solve the puzzle.");

        puz = Solver.solvePuzzle(puz, true);

        System.out.println("Here is your solved puzzle.");
        puz.print();

    }

    public static void promptEnterKey(String prompt) {
        System.out.println(prompt);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
