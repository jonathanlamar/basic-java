package basic.sudoku;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Generator {

    /**
     * Scrape a new random puzzle from the sudokuweb.org
     * @param print - Whether to show the puzzle
     * @return - Puzzle instance
     * @throws IOException
     * @throws Error - If the puzzle is not 9x9
     */
    public static Puzzle getNewPuzzle(boolean print) throws IOException, Error {
        Document page = Jsoup.connect("https://www.sudokuweb.org").get();

        Element grid = page.select("div.sudoku").first();

        Puzzle puz = parseFromDivElt(grid);

        if (print) puz.print();

        return puz;
    }


    /**
     * Scrape a new random puzzle from the sudokuweb.org
     * @return - Puzzle instance
     * @throws IOException
     * @throws Error - If the puzzle is not 9x9
     */
    public static Puzzle getNewPuzzle() throws IOException, Error {
        return getNewPuzzle(false);
    }


    /**
     * Parse a div Element from the html source of the webpage.
     * @param grid - div element representing the puzzle
     * @return - puzzle instance
     * @throws Error - If the puzzle is not 9x9.
     */
    private static Puzzle parseFromDivElt(Element grid) throws Error {
        Elements rows = grid.select("tr");

        if (rows.size() != 9) throw new Error("Wrong row size.");

        int[][] outGrid = new int[9][9];

        for (int i = 0; i < 9; i++) {
            Element row = rows.get(i);
            outGrid[i] = getRow(row);
        }

        return new Puzzle(outGrid);

    }


    /**
     * Get row from a tr Element
     * @param row - tr Element representing puzzle row
     * @return - int[9] containing 1-9 int for the filled cells, and blanks
     * imputed as 0
     * @throws Error - if the row does not contain 9 cells.
     */
    private static int[] getRow(Element row) throws Error {
        Elements cells = row.select("td");

        if (cells.size() != 9) throw new Error("Not a 9x9 grid.");

        int[] outArr = new int[9];
        for (int i = 0; i < 9; i++) {

            Element cell = cells.get(i);

            // class sedy contains the filled cells.
            Element span = cell.select("span.sedy").first();

            if (span == null) {
                outArr[i] = 0;
            } else {
                outArr[i] = Integer.parseInt(span.text());
            }
        }


        return outArr;
    }
}
