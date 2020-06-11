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
        double[][] rect = getRectangle();
        String str = "";

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (rect[i][j] < 0.2) str += " ";

                // Light shade
                else if (rect[i][j] < 0.4) str += "\u2591";

                // Medium shade
                else if (rect[i][j] < 0.6) str += "\u2592";

                // Heavy shade
                else if (rect[i][j] < 0.8) str += "\u2593";

                // Full block
                else  str += "\u2588";
            }
            str += "\n";
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
     * @throws Error if the rectangle has uneven rows.
     */
    private static double[] flatten(double[][] rect)
    throws Error 
    {
        int h = rect.length;
        int w = rect[0].length;

        double[] arr = new double[h*w];

        for (int i = 0; i < h; i++) {

            if (rect[i].length != w) {
                throw new Error("Unequal width of rows.");
            }

            for (int j = 0; j < w; j++) {
                arr[w*i + j] = rect[i][j];
            }
        }

        return arr;
    }


}
