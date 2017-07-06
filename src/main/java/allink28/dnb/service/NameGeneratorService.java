package allink28.dnb.service;

import allink28.dnb.domain.Character;
import allink28.dnb.domain.enumeration.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by Allink on 7/5/2017.
 */
public class NameGeneratorService {
    private static final Logger log = LoggerFactory.getLogger(CharacterGeneratorService.class);

    private static final String[] PREFIX = {
        "A", "Al", "An", "Ang", "B", "Br", "C", "Ch", "Chr", "D", "E", "Er",
        "F", "G", "Gr", "H", "I", "In", "J", "K", "Kn", "L", "M", "N", "P",
        "Qu", "R", "S", "Sh", "St", "T", "Tr", "Th", "V", "W", "X", "Y", "Z"};
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "y"};
    private static final String[] MPOSTFIX = {
        "a", "an", "b", "brey", "c", "ch", "ck", "d", "e", "en", "f",
        "g", "gh", "h", "i", "ith", "j", "k",
        "l", "le", "ll", "lo", "m", "n", "nas", "nes", "net", "non",
        "p", "que", "r", "ret", "rt", "ry", "s", "st", "t", "tor", "th",
        "v", "ven", "w", "wart", "x", "ya", "z"};
    private static final String[] FPOSTFIX = {
        "a", "an", "b", "brey", "c", "ch", "ck", "d", "e", "en", "f",
        "g", "gh", "h", "i", "ie", "in", "ith", "ja", "k",
        "la", "lle", "ley", "ll", "m", "n", "nas", "nes", "net",
        "p", "que", "r", "ret", "rt", "ry", "ss", "st", "tte", "ty", "th",
        "v", "ven", "w", "xa", "xi", "y", "ya", "z"};

    private static Random rand = new Random();

    public static String generateName(Character character) {
        if (character.getSex() == Sex.Male)
            return generateMaleName();
        return generateFemaleName();
    }

    public static String generateMaleName() {
        return PREFIX[rand.nextInt(PREFIX.length)] +
            VOWELS[rand.nextInt(VOWELS.length)] +
            MPOSTFIX[rand.nextInt(MPOSTFIX.length)];
    }

    public static String generateFemaleName() {
        return PREFIX[rand.nextInt(PREFIX.length)] +
            VOWELS[rand.nextInt(VOWELS.length)] +
            FPOSTFIX[rand.nextInt(FPOSTFIX.length)];
    }

}
