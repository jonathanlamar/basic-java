package basic.sudoku;

import java.util.Scanner;

public class Sudoku {
    public static void main(String[] args) {

        Scanner fileIn = new Scanner(System.in); 
        System.out.println("Please enter a csv file containing a sudoku puzzle."); 
        String fileName = fileIn.nextLine(); 
        fileIn.close();
        System.out.println("You entered this file: " + fileName);

        try {
            int[][] sudokuGrid = Parser.readFromCsv(fileName); 
            boolean isValid = Checker.checkGrid(sudokuGrid);
            if (isValid) {
                System.out.println("It's good.");
            } else {
                System.out.println("It's not good.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
