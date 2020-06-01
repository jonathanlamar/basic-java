package basic.sudoku;

import java.io.IOException;

public class Sudoku {
    public static void main(String[] args) throws IOException {

        promptEnterKey("Press Enter to grab a sudoku puzzle."); 

        Puzzle puz = Generator.getNewPuzzle();

        System.out.println("Here is your puzzle.");
        System.out.println(puz.getStr());
        
        promptEnterKey("Press Enter to solve the puzzle.");

        puz = Solver.solvePuzzle(puz);

        System.out.println("Here is your solved puzzle.");
        System.out.println(puz.getStr());

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
