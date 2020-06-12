package basic.ascii;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Sprite {
    int x;
    int y;

    // TODO: Does this break if I launch the jar elsewhere?
    static BufferedImage spriteSheet = loadImage("src/main/resources/chars.bmp");
    static int width = 11;
    static int height = 20;


    /**
     * Create a sprite instance
     * @param x - horizontal coordinate (number of cells from the left)
     * @param y - vertical coordinate (number of cells from the top)
    */
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Get pixel coordinates of sprite top left corner
     * @return int[2] = {x, y}
     */
    public int[] getCoords() {
        return new int[] {11*this.x, 20*this.y};
    }


    /**
     * Return pixels of rectangle defined by (x,y,w,h)
     * @return - double array of pixel values.
     */
    public double[][] getRectangle() {
        double[] pixels = new double[width*height];

        int[] coords = getCoords();

        spriteSheet.getData().getPixels(
            coords[0], coords[1], 
            width, height,
            pixels);

        double[][] out = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                out[i][j] = pixels[width*i + j] / 255.0;
            }
        }

        return out;
    }


    /**
     * Get mean of array of doubles
     * @return mean
     * @throws Error if the rectangle has uneven rows.
     */
    public double pixelMean() 
    throws Error 
    {
        double[][] rect = getRectangle();
        double[] arr = flatten(rect);

        return Arrays.stream(arr).average().getAsDouble();
    }


    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < height; i++) {
            str += stringRow(i) + "\n";
        }

        return str;
    }


    /**
     * Get row of string, for gluing words.
     * @param row - which row
     * @return - Just the row
     * @throws Error if row is out of range
     */
    public String stringRow(int row) 
    throws Error 
    {
        if (row < 0 || row >= 20) {
            throw new Error("invalid row number");
        }

        double[][] rect = getRectangle();
        String str = new String();

        for (int c = 0; c < width; c++) {
            if (rect[row][c] < 0.2) str += " ";

            // Light shade
            else if (rect[row][c] < 0.4) str += "\u2591";

            // Medium shade
            else if (rect[row][c] < 0.6) str += "\u2592";

            // Heavy shade
            else if (rect[row][c] < 0.8) str += "\u2593";

            // Full block
            else  str += "\u2588";
        }

        return str;
    }



    /** PRIVATE METHODS */


    /**
     * Loads an image
     * @param fileName - Relative path to file.
     * @return BufferedImage instance
     */
    private static BufferedImage loadImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) { }

        return img;
    }


    /**
     * Flatten rectangle into 1D array.
     * @param rect - 2D array
     * @return flattened array
     * @throws Error if the rectangle has incorrect dimension
     */
    private static double[] flatten(double[][] rect)
    throws Error 
    {
        double[] arr = new double[height * width];

        if (rect.length != height) throw new Error("Wrong number of rows.");

        for (int i = 0; i < height; i++) {

            if (rect[i].length != width) {
                throw new Error("Unequal width of rows.");
            }

            for (int j = 0; j < width; j++) {
                arr[width*i + j] = rect[i][j];
            }
        }

        return arr;
    }


}
