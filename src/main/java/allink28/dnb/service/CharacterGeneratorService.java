package allink28.dnb.service;

import allink28.dnb.domain.Character;
import allink28.dnb.domain.enumeration.Alignment;
import allink28.dnb.domain.enumeration.Classes;
import allink28.dnb.domain.enumeration.Race;
import allink28.dnb.domain.enumeration.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by allenpreville on 6/25/17.
 */
public class CharacterGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(CharacterGeneratorService.class);

    private static final String[] PREFIX = {"A", "Al", "B", "Br", "C", "Ch", "D",
        "F", "G", "H", "I", "J", "K", "Kn", "L", "M", "N", "P", "Qu", "R", "S",
        "St", "T", "Tr", "Th", "V", "W", "X", "Y", "Z"};
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "y"};
    private static final String[] POSTFIX = {"a", "b", "c", "ch", "ck", "d",
        "f", "g", "gh", "h", "i", "j", "k", "ll", "m", "n", "p", "que", "r", "s",
        "st", "t", "th", "v", "w", "x", "y", "z"};

    private static Random rand = new Random();

    public static String generateName() {
        return PREFIX[rand.nextInt(PREFIX.length)] +
            VOWELS[rand.nextInt(VOWELS.length)] +
//            PREFIX[rand.nextInt(PREFIX.length)] +
//            VOWELS[rand.nextInt(VOWELS.length)] +
            POSTFIX[rand.nextInt(POSTFIX.length)];

    }

    public static Race randomRace() {
        Race[] races = Race.values();
        return races[rand.nextInt(races.length)];
    }

    public static String randomClass() {
        Classes[] classes = Classes.values();
        return classes[rand.nextInt(classes.length)].toString();
    }

    public static Sex randomSex() {
        Sex[] sexes = Sex.values();
        return sexes[rand.nextInt(sexes.length)];
    }

    public static Alignment randomAlignment() {
        return Alignment.ALIGNMENTS[rand.nextInt(Alignment.ALIGNMENTS.length)];
    }

    /*
     * Possible TODO: Random alignment based on Character's class and race.
     * E.g., Paladins more likely to be lawful. Half-elves more likely to be chaotic.
     */
    public static Alignment randomAlignment(Character character) {
        String characterClass = character.getClasses();
        Race race = character.getRace();
        try {
            Classes thisClass = Classes.valueOf(characterClass);
            switch (thisClass) {
                case Paladin:

                    break;
                case Cleric:

                    break;

            }
        } catch (IllegalArgumentException e) {
            log.error("Unable to parse class: " + characterClass + ". " + e.getMessage() + " Disregarding class for alignment random alignment.");
//            return randomAlignment();
        }

        switch (race) {
            case Half_Elf:

                break;
            case Tiefling:

                break;
            case Gnome:

                break;

            default:
                return randomAlignment();
        }

        return randomAlignment();
    }

//    /**
//     * Generate a random character height based on race and sex.
//     */
//    public static String randomHeight(Character character) {
//
//    }

//    /**
//     * Generate a random character weight based on height?
//     */
//    public static String randomWeight(Character character) {
//
//    }


    /**
     * Bounded bell curve.
     * Approximates a bell curve by adding and subtracting
     */
    public static int plusMinusRandomBellCurve(int midpoint, int distToMax) {
        return midpoint + rand.nextInt(distToMax) - rand.nextInt(distToMax);
    }

    /**
     * Bounded bell curve.
     * Approximates a bell curve by
     */
    public static int boundedRandomBellcurve(int midpoint, int distToMax) {
        //Start at lowerbound
        return midpoint - distToMax +
            //and then add the average of two random numbers
            (int) Math.round((2*distToMax * rand.nextDouble() + 2*distToMax * rand.nextDouble())/2.0);
    }

    /**
     * Bellcurve that is theoretically unbounded.
     */
    public static int gaussianRandom(int midpoint, int distToMax) {
        double gaussian = rand.nextGaussian();
        return (int)(midpoint + (gaussian * distToMax));
    }
}
