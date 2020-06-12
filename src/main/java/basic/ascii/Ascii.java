package basic.ascii;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Ascii {

    // Sprites for each character
    public static HashMap<String, Sprite> spriteMap = 
        new HashMap<String, Sprite>();

    // Initialize sprites
    static {
        spriteMap.put("a", new Sprite(0, 0));     spriteMap.put("A", new Sprite(0, 1)); 
        spriteMap.put("b", new Sprite(1, 0));     spriteMap.put("B", new Sprite(1, 1));
        spriteMap.put("c", new Sprite(2, 0));     spriteMap.put("C", new Sprite(2, 1));
        spriteMap.put("d", new Sprite(3, 0));     spriteMap.put("D", new Sprite(3, 1));
        spriteMap.put("e", new Sprite(4, 0));     spriteMap.put("E", new Sprite(4, 1));
        spriteMap.put("f", new Sprite(5, 0));     spriteMap.put("F", new Sprite(5, 1));
        spriteMap.put("g", new Sprite(6, 0));     spriteMap.put("G", new Sprite(6, 1));
        spriteMap.put("h", new Sprite(7, 0));     spriteMap.put("H", new Sprite(7, 1));
        spriteMap.put("i", new Sprite(8, 0));     spriteMap.put("I", new Sprite(8, 1));
        spriteMap.put("j", new Sprite(9, 0));     spriteMap.put("J", new Sprite(9, 1));
        spriteMap.put("k", new Sprite(10, 0));    spriteMap.put("K", new Sprite(10, 1));
        spriteMap.put("l", new Sprite(11, 0));    spriteMap.put("L", new Sprite(11, 1));
        spriteMap.put("m", new Sprite(12, 0));    spriteMap.put("M", new Sprite(12, 1));
        spriteMap.put("n", new Sprite(13, 0));    spriteMap.put("N", new Sprite(13, 1));
        spriteMap.put("o", new Sprite(14, 0));    spriteMap.put("O", new Sprite(14, 1));
        spriteMap.put("p", new Sprite(15, 0));    spriteMap.put("P", new Sprite(15, 1));
        spriteMap.put("q", new Sprite(16, 0));    spriteMap.put("Q", new Sprite(16, 1));
        spriteMap.put("r", new Sprite(17, 0));    spriteMap.put("R", new Sprite(17, 1));
        spriteMap.put("s", new Sprite(18, 0));    spriteMap.put("S", new Sprite(18, 1));
        spriteMap.put("t", new Sprite(19, 0));    spriteMap.put("T", new Sprite(19, 1));
        spriteMap.put("u", new Sprite(20, 0));    spriteMap.put("U", new Sprite(20, 1));
        spriteMap.put("v", new Sprite(21, 0));    spriteMap.put("V", new Sprite(21, 1));
        spriteMap.put("w", new Sprite(22, 0));    spriteMap.put("W", new Sprite(22, 1));
        spriteMap.put("x", new Sprite(23, 0));    spriteMap.put("X", new Sprite(23, 1));
        spriteMap.put("y", new Sprite(24, 0));    spriteMap.put("Y", new Sprite(24, 1));
        spriteMap.put("z", new Sprite(25, 0));    spriteMap.put("Z", new Sprite(25, 1));
        spriteMap.put(" ", new Sprite(26, 0));

        spriteMap.put("0", new Sprite(0, 2)); 
        spriteMap.put("1", new Sprite(1, 2)); 
        spriteMap.put("2", new Sprite(2, 2)); 
        spriteMap.put("3", new Sprite(3, 2)); 
        spriteMap.put("4", new Sprite(4, 2)); 
        spriteMap.put("5", new Sprite(5, 2)); 
        spriteMap.put("6", new Sprite(6, 2)); 
        spriteMap.put("7", new Sprite(7, 2)); 
        spriteMap.put("8", new Sprite(8, 2)); 
        spriteMap.put("9", new Sprite(9, 2)); 
        spriteMap.put(".", new Sprite(10, 2));
        spriteMap.put(":", new Sprite(11, 2));
        spriteMap.put(",", new Sprite(12, 2));
        spriteMap.put(";", new Sprite(13, 2));
        spriteMap.put("(", new Sprite(14, 2));
        spriteMap.put(")", new Sprite(20, 2));
        spriteMap.put("*", new Sprite(15, 2));
        spriteMap.put("!", new Sprite(16, 2));
        spriteMap.put("?", new Sprite(17, 2));
        spriteMap.put("#", new Sprite(21, 2));
        spriteMap.put("$", new Sprite(22, 2));
        spriteMap.put("{", new Sprite(23, 2));
        spriteMap.put("}", new Sprite(18, 2));
        spriteMap.put("%", new Sprite(24, 2));
        spriteMap.put("^", new Sprite(25, 2));
        spriteMap.put("&", new Sprite(26, 2));
        spriteMap.put("-", new Sprite(27, 2));
        spriteMap.put("+", new Sprite(28, 2));
        spriteMap.put("@", new Sprite(29, 2));

    }


    public static String[] charsInOrder = sortCharsByMean();


    public static String spellWord(String word) {
        String outStr = new String();
        for (int i = 0; i < Sprite.height; i++) {
            for (int j = 0; j < word.length(); j++) {

                String letter = word.substring(j, j+1);
                Sprite sprite = spriteMap.get(letter);

                outStr += sprite.stringRow(i);
            }
            outStr += "\n";
        }

        return outStr;
    }


    /** PRIVATE METHODS */

    /**
     * Sort by mean of bitmap sprite
     * @return Strings in ascending order
     */
    private static String[] sortCharsByMean() {
        String[] keys = new String[spriteMap.size()];

        keys = spriteMap.keySet().toArray(keys);

        Arrays.sort(keys, compareByMean);

        return keys;
    }

    /** For comparing by bitmap mean */
    private static Comparator<String> compareByMean = new Comparator<String>() {
        @Override
        public int compare(String str1, String str2) {
            double val1 = spriteMap.get(str1).pixelMean();
            double val2 = spriteMap.get(str2).pixelMean();

            if (val1 > val2) return 1;
            else if (val1 < val2) return -1;
            else return 0;
        }
    };


}
