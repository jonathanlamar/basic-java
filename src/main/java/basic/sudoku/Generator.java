package basic.sudoku;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Generator {
    public Puzzle getNewPuzzle() throws IOException, Error {
        Document page = Jsoup.connect("https://www.sudokuweb.org").get();

        Element grid = page.select("div.sudoku").first();

        return parseFromDivElt(grid);
    }


    private Puzzle parseFromDivElt(Element grid) throws Error {
        Elements rows = grid.select("tr");

        if (rows.size() != 9) throw new Error("Wrong row size.");

        int[][] outGrid = new int[9][9];

        for (int i = 0; i < 9; i++) {
            Element row = rows.get(i);
            outGrid[i] = getRow(row);
        }

        return new Puzzle(outGrid);

    }


    private int[] getRow(Element row) throws Error {
        Elements cells = row.select("td");

        if (cells.size() != 9) throw new Error("Not a 9x9 grid.");

        int[] outArr = new int[9];
        for (int i = 0; i < 9; i++) {

            Element cell = cells.get(i);

            // class sedy contains the filled cells.
            Element span = cell.select("span.sedy").first();

            if (span == null) {
                outArr[i] = -1;
            } else {
                outArr[i] = Integer.parseInt(span.text());
            }
        }


        return outArr;
    }
}
