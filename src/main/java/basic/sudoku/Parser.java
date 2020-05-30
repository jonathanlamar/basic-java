package basic.sudoku;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Parser {

    public static int[][] readFromCsv(String fileString) throws FileNotFoundException, IOException, Error {

        Reader fileIn = new FileReader(fileString);
        CSVParser parser = new CSVParser(fileIn, CSVFormat.DEFAULT.withDelimiter(',')); 

        List<CSVRecord> records = parser.getRecords();

        parser.close();

        if ( records.size() != 9 ) throw new Error("Incorrect number of rows.");

        int[][] parsedArr = new int[9][9];

        for (int r = 0; r < 9; r++) {
            System.out.println("Row " + r);
            for (int c = 0; c < 9; c++) {
                String cellVal = records.get(r).get(c);
                parsedArr[r][c] = parseCell(cellVal);
            }
        }

        return parsedArr;

    }

    public static int parseCell(String cellVal) {

        int out;

        System.out.println(cellVal);
        if (cellVal.equals(".")) {
            System.out.println("Equals period");
            out = -1;
        } else {
            System.out.println("Does not equal period");
            out = Integer.parseInt(cellVal);
        }

        return out;
    }
}
