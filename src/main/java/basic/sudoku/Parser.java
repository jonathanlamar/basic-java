package basic.sudoku;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Parser {

    /**
     * Read csv and parse as aPuzzle instance
     * @param fileString - filename with path relative to where jshell was launched.
     * @return - Puzzle instance
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Error - If puzzle is not 9x9
     */
    public static Puzzle readFromCsv(String fileString) throws FileNotFoundException, IOException, Error {

        Reader fileIn = new FileReader(fileString);
        CSVReader reader = new CSVReader(fileIn);

        List<String[]> records = reader.readAll();

        reader.close();

        if ( records.size() != 9 ) throw new Error("Incorrect number of rows.");

        int[][] parsedArr = new int[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String cellVal = records.get(r)[c];
                parsedArr[r][c] = parseCell(cellVal);
            }
        }

        return new Puzzle(parsedArr);

    }

    /**
     * Empty cells are encoded as periods.  This method parses that encoding
     * and returns an integer with empty cells encoded as 0
     * @param cellVal - Value of csv cell
     * @return - integer encoding
     */
    public static int parseCell(String cellVal) {

        int out;

        if (cellVal.equals(".")) {
            out = 0;
        } else {
            out = Integer.parseInt(cellVal);
        }

        return out;
    }



    /**
     * Save puzzle as a csv file.
     * @param puz - Puzzle to save
     * @param outPath - file name, with path relative to where the jshell was launched
     * @throws IOException
     */
    public static void saveToCsv(Puzzle puz, String outPath) throws IOException {
        Writer fileOut = new FileWriter(outPath);
        CSVWriter writer = new CSVWriter(fileOut,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);

        int[][] grid = puz.getGrid();

        for (int r = 0; r < 9; r++) {
            String[] rowStr = new String[9];

            for (int c = 0; c < 9; c++) {
                String cellStr;

                if (grid[r][c] == 0) cellStr = ".";
                else cellStr = Integer.toString(grid[r][c]);

                rowStr[c] = cellStr;
            }

            writer.writeNext(rowStr);
        }

        writer.close();
    }
}
