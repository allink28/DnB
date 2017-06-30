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

    private static final String[] PREFIX = {
        "A", "Al", "An", "Ang", "B", "Br", "C", "Ch", "Chr", "D", "E", "Er",
        "F", "G", "Gr", "H", "I", "In", "J", "K", "Kn", "L", "M", "N", "P",
        "Qu", "R", "S", "Sh", "St", "T", "Tr", "Th", "V", "W", "X", "Y", "Z"};
    private static final String[] VOWELS = {"a", "e", "i", "o", "u", "y"};
    private static final String[] POSTFIX = {
        "a", "an", "b", "brey", "c", "ch", "ck", "d", "e", "en", "f",
        "g", "gh", "h", "i", "ith", "j", "k",
        "la", "le", "ley", "ll", "lo", "m", "n", "nas", "nes", "net", "non",
        "p", "que", "r", "ret", "rt", "ry", "s", "st", "t", "tor", "th",
        "v", "ven", "w", "wart", "x", "y", "ya", "z"};

    private static Random rand = new Random();

    public static String generateName() {
        return PREFIX[rand.nextInt(PREFIX.length)] +
            VOWELS[rand.nextInt(VOWELS.length)] +
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
                    if (rand.nextInt(2) == 0) //~50% of being lawful good
                        return Alignment.Lawful_Good;
                    break;
                case Monk:
                    if (rand.nextInt(2) == 0) { //~50% of being Lawful
                        return Alignment.GRID[rand.nextInt(3)][Alignment.LAWFUL_COLUMN];
                    }
                    break;
                case Druid:
                    if (rand.nextInt(2) == 0) { //~50% of being neutral on at least one axis
                        Alignment[] druidAlignments = { Alignment.Neutral_Good, Alignment.True_Neutral,
                            Alignment.Neutral_Evil, Alignment.Lawful_Neutral, Alignment.Chaotic_Neutral
                        };
                        return druidAlignments[rand.nextInt(druidAlignments.length)];
                    }
                    break;
                case Rogue:
                    if (rand.nextInt(2) == 0) { //~50% of being not Lawful-Good
                        Alignment[] rogueAlignments = { Alignment.Neutral_Good, Alignment.Chaotic_Good, Alignment.True_Neutral,
                            Alignment.Neutral_Evil, Alignment.Lawful_Neutral, Alignment.Chaotic_Neutral, Alignment.Lawful_Evil, Alignment.Chaotic_evil
                        };
                        return rogueAlignments[rand.nextInt(rogueAlignments.length)];
                    }
                    break;
                case Bard: //Any non-Lawful alignment?
                    if (rand.nextInt(3) == 0) { //~33% of being not Lawful
                        Alignment[] rogueAlignments = { Alignment.Neutral_Good, Alignment.Chaotic_Good, Alignment.True_Neutral,
                            Alignment.Neutral_Evil, Alignment.Chaotic_Neutral, Alignment.Chaotic_evil
                        };
                        return rogueAlignments[rand.nextInt(rogueAlignments.length)];
                    }
                    break;
                case Warlock:
                    if (rand.nextInt(4) == 0) { //~25% of being evil
                        return Alignment.GRID[Alignment.EVIL_ROW][rand.nextInt(3)];
                    }
                    break;
                case Barbarian:
                    if (rand.nextInt(4) == 0) { //~25% chaotic
                        return Alignment.GRID[rand.nextInt(3)][Alignment.CHAOTIC_COLUMN];
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            log.error("Unable to parse class: " + characterClass + ". " + e.getMessage() + " Disregarding class for alignment random alignment.");
        }

        switch (race) {
            case Dwarf:
            case Halfling:
                if (rand.nextInt(2) == 0) { //~50% Lawful Good
                    return Alignment.Lawful_Good;
                }
                break;
            case Gnome:
                if (rand.nextInt(2) == 0) { //~50% Neutral Good
                    return Alignment.Neutral_Good;
                }
                break;
            case Elf:
                if (rand.nextInt(2) == 0) { //~50% Chaotic Good
                    return Alignment.Chaotic_Good;
                }
                break;
            case Half_Orc:
                if (rand.nextInt(3) == 0) { //~33% Chaotic
                    return Alignment.GRID[rand.nextInt(3)][Alignment.CHAOTIC_COLUMN];
                }
                break;
            case Half_Elf:
                return Alignment.GRID[rand.nextInt(3)][Alignment.CHAOTIC_COLUMN];
            case Tiefling:
                if (rand.nextInt(4) == 0) { //~25% of being evil
                    return Alignment.GRID[Alignment.EVIL_ROW][rand.nextInt(3)];
                }
                break;
            case Dragonborn:
                int goodness = rand.nextInt(10);
                if (goodness > 4) { //~50% chance of good
                    return Alignment.Lawful_Good;
                } else if (goodness < 4) { //~40% of evil
                    return Alignment.Chaotic_evil;
                }
                break;
        }

        return randomAlignment();
    }

    /**
     * Generate a random character height (in inches) based on race and sex.
     */
    public static String randomHeight(Character character) {
        int height;
        Race race = character.getRace();

        switch (race) {
            case Human:
                //67" average male height, 62" average female
                height = dndRandom(56, 10,10);
                if (Sex.Female == character.getSex()) {
                    height -= rand.nextInt(5);
                }
                break;
            case Dwarf:
                //4' for mountain dwarf, 3'8" for hill dwarf. Todo What do?
                height = dndRandom(48, 4,4);
                break;
            case Elf:
                height = dndRandom(54, 10,10);
                break;
            case Halfling:
                height = dndRandom(21, 4,4);
                break;
            case Dragonborn:
                height = dndRandom(66, 8,8);
                break;
            case Gnome:
                height = dndRandom(35, 4,4);
                break;
            case Half_Elf:
                height = dndRandom(57, 8,8);
                break;
            case Half_Orc:
                height = dndRandom(58, 10,10);
                break;
            case Tiefling:
                height = dndRandom(57, 8,8);
                break;
            default:
                log.error("Unknown race " + race);
                return null;
        }

        return inchesToFormattedHeight(height);
    }

    public static String inchesToFormattedHeight(int inches) {
        return inches/12 + "'" + inches%12+"\"";
    }

//    /**
//     * Generate a random character weight based on height?
//     */
//    public static String randomWeight(Character character) {
//
//    }

    public static int dndRandom(int base, int... diceModifier) {
        for (int i = 0; i < diceModifier.length; ++i) {
            base += rand.nextInt(diceModifier[i]) + 1;
        }
        return base;
    }

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
