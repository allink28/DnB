package allink28.dnb.service;

import java.util.Random;

/**
 * Created by allenpreville on 6/25/17.
 */
public class CharacterGeneratorService {
    private static final String[] PREFIX = {"A", "B", "Br", "C", "Ch", "D",
        "F", "G", "H", "I", "J", "K", "Kn", "L", "M", "N", "P", "Qu", "R", "S",
        "St", "T", "Th", "V", "W", "X", "Y", "Z"};
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "y"};
    private static final String[] POSTFIX = {"a", "b", "c", "ch", "ck", "d",
        "f", "g", "gh", "h", "i", "j", "k", "ll", "m", "n", "p", "que", "r", "s",
        "st", "t", "th", "v", "w", "x", "y", "z"};

    private static Random rand = new Random();

    public static String generateName() {
        return PREFIX[rand.nextInt(PREFIX.length)] +
            VOWELS[rand.nextInt(VOWELS.length)]+
            POSTFIX[rand.nextInt(POSTFIX.length)];

    }
}