package co.pixelmc.helpers;

import java.util.Arrays;
import java.util.List;

public class TextHelpers {
    private static List<String> codes = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "j", "k", "l", "m", "n", "o");

    public static String replaceCodes(String string){
        for (String code : codes) {
            string = string.replace("&"+code, "\u00a7"+code);
        }
        return string;
    }
}