package allink28.dnb.service;

import allink28.dnb.domain.Character;
import allink28.dnb.domain.enumeration.Alignment;
import allink28.dnb.domain.enumeration.Classes;
import allink28.dnb.domain.enumeration.Race;
import allink28.dnb.domain.enumeration.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        try {
            Classes thisClass = Classes.valueOf(character.getClasses());
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
                case Bard:
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
            log.error("Unable to parse class: " + character.getClasses() + ". " + e.getMessage() + " Disregarding class for alignment. Selecting random alignment.");
        }

        switch (character.getRace()) {
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

    /**
     * Generate a random character height and weight based on race
     */
    public static void setRandomHeightWeight(Character character) {
        int heightModifier;
        Race race = character.getRace();

        switch (race) {
            case Human:
                heightModifier = rollDie(10,10);
                //67" average male height, 62" average female
                if (Sex.Female == character.getSex()) {
                    heightModifier -= rand.nextInt(6);
                    heightModifier = Math.max(0, heightModifier);
                }
                character.setHeight(inchesToFormattedHeight(56 + heightModifier));
                character.setWeight(110  + heightModifier * rollDie(4,4));
                break;
            case Dwarf:
                //4' for mountain dwarf, 3'8" for hill dwarf. Todo What do?
                heightModifier = rollDie(4,4);
                character.setHeight(inchesToFormattedHeight(48 + heightModifier));
                character.setWeight(130  + heightModifier * rollDie(6,6));
                break;
            case Elf:
                heightModifier = rollDie(10,10);
                character.setHeight(inchesToFormattedHeight(54 + heightModifier));
                character.setWeight(100  + heightModifier * rollDie(4,4));//wood elf
                break;
            case Halfling:
                heightModifier = rollDie(4,4);
                character.setHeight(inchesToFormattedHeight(21 + heightModifier));
                character.setWeight(35  + heightModifier);
                break;
            case Dragonborn:
                heightModifier = rollDie(8,8);
                character.setHeight(inchesToFormattedHeight(66 + heightModifier));
                character.setWeight(175  + heightModifier * rollDie(6,6));
                break;
            case Gnome:
                heightModifier = rollDie(4,4);
                character.setHeight(inchesToFormattedHeight(35 + heightModifier));
                character.setWeight(35  + heightModifier);
                break;
            case Half_Elf:
                heightModifier = rollDie(8,8);
                character.setHeight(inchesToFormattedHeight(57 + heightModifier));
                character.setWeight(110  + heightModifier * rollDie(4,4));
                break;
            case Half_Orc:
                heightModifier = rollDie(10,10);
                character.setHeight(inchesToFormattedHeight(58 + heightModifier));
                character.setWeight(140  + heightModifier * rollDie(6,6));
                break;
            case Tiefling:
                heightModifier = rollDie(8,8);
                character.setHeight(inchesToFormattedHeight(57 + heightModifier));
                character.setWeight(110  + heightModifier * rollDie(4,4));
                break;
            default:
                log.error("Unknown race " + race);
        }
    }

    public static void generateStats(Character character) {
        List<Integer> rolls = new ArrayList<>(6);
        for (int i = 0; i < 5; ++i) {
            rolls.add(rollStat());
        }
        Collections.sort(rolls);
        try {
            Classes thisClass = Classes.valueOf(character.getClasses());
            switch (thisClass) {
                case Paladin:

                    break;
                case Monk:

                    break;
                case Druid:

                    break;
                case Rogue:

                    break;
                case Bard:
                    character.setCharisma(rolls.remove(rolls.size() - 1));
                    Collections.shuffle(rolls);
//                    character.setCo
                    character.setStrength(rolls.remove(0));
                    character.setDexterity(rolls.remove(0));
                    character.setWisdom(rolls.remove(0));
                    character.setIntelligence(rolls.remove(0));
                    break;
                case Warlock:
                    character.setCharisma(rolls.remove(rolls.size() - 1));
                    character.setStrength(rolls.remove(0));
                    Collections.shuffle(rolls);
                    character.setDexterity(rolls.remove(0));
                    character.setWisdom(rolls.remove(0));
                    character.setIntelligence(rolls.remove(0));
                    break;
                case Barbarian:
                    character.setStrength(rolls.remove(rolls.size() - 1));
                    Collections.shuffle(rolls);
                    character.setStrength(rolls.remove(0));
                    character.setDexterity(rolls.remove(0));
                    character.setWisdom(rolls.remove(0));
                    character.setIntelligence(rolls.remove(0));
                    break;
                case Sorcerer:
                    character.setCharisma(rolls.remove(rolls.size() - 1));
                    Collections.shuffle(rolls);
                    character.setStrength(rolls.remove(0));
                    character.setDexterity(rolls.remove(0));
                    character.setWisdom(rolls.remove(0));
                    character.setIntelligence(rolls.remove(0));
                    break;
                case Fighter:

                    break;
                case Wizard:

                    break;
                case Ranger:

                    break;
                case Cleric:

                    break;
                default:
            }
        } catch (IllegalArgumentException e) {
            log.error("Unable to parse class: " + character.getClasses() + ". " + e.getMessage() + " Disregarding class for stat allocation. Randomly generating.");
            Collections.shuffle(rolls);
            character.setStrength(rolls.remove(0));
            character.setDexterity(rolls.remove(0));
            character.setWisdom(rolls.remove(0));
            character.setIntelligence(rolls.remove(0));
            character.setCharisma(rolls.remove(0));
        }
        addRaceStatBonuses(character);
        //calculate maxHP
    }

    private static void addRaceStatBonuses(Character character) {
        switch (character.getRace()) {
            case Human:

                break;
            case Dwarf:

                break;
            case Elf:

                break;
            case Halfling:

                break;
            case Dragonborn:

                break;
            case Gnome:

                break;
            case Half_Elf:

                break;
            case Half_Orc:

                break;
            case Tiefling:

                break;
            default:
                log.error("Unknown race " + character.getRace());
        }
    }

    public static int rollStat() {
        int lowestRoll = 6;
        int total = 3;
        for (int i = 0; i < 3; ++i) {
            int roll = rand.nextInt(6) + 1;
            total += roll;
            lowestRoll = Math.min(lowestRoll, roll);
        }
        total -= lowestRoll;
        return total;
    }

    public static int rollDie(int... dieSides) {
        int sum = 0;
        for (int dieSide : dieSides) {
            sum += rand.nextInt(dieSide) + 1;
        }
        return sum;
    }

    public static int dndRandom(int base, int... diceModifier) {
        for (int dieModifier : diceModifier) {
            base += rand.nextInt(dieModifier) + 1;
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
