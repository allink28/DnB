package allink28.dnb.service;

import allink28.dnb.domain.Character;
import allink28.dnb.domain.enumeration.Race;
import allink28.dnb.domain.enumeration.Sex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by allenpreville on 6/25/17.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DnBApp.class)
public class CharacterGeneratorServiceTest {

    private final Logger log = LoggerFactory.getLogger(CharacterGeneratorServiceTest.class);

    @Test
    public void generateNameTest() {
        StringBuilder logOutput = new StringBuilder("\n");
        for (int i = 0; i < 50; ++i) {
            String name = CharacterGeneratorService.generateName();
            assertTrue("Name: " + name + " is not correct.",
                !name.isEmpty() && java.lang.Character.isUpperCase(name.charAt(0)));
            logOutput.append(name).append(" ");
        }
        log.info(logOutput.toString());
    }

    @Test
    public void randomRaceTest() {
        StringBuilder logOutput = new StringBuilder("\n");
        for (int i = 0; i < 20; ++i) {
            String race = CharacterGeneratorService.randomRace().toString();
            assertTrue("Race: " + race + " is not correct.",
                !race.isEmpty() && java.lang.Character.isUpperCase(race.charAt(0)));
            logOutput.append(race).append(" ");
        }
        log.info(logOutput.toString());
    }

    @Test
    public void randomClassTest() {
        StringBuilder logOutput = new StringBuilder("\n");
        for (int i = 0; i < 20; ++i) {
            String aClass = CharacterGeneratorService.randomClass();
            assertTrue("Class: " + aClass + " is not correct.",
                !aClass.isEmpty() && java.lang.Character.isUpperCase(aClass.charAt(0)));
            logOutput.append(aClass).append(" ");
        }
        log.info(logOutput.toString());
    }

    @Test
    public void randomHeightTest() {
        Character character = new Character();
        character.setRace(Race.Human);
        character.setSex(Sex.Male);
        for (int i = 0; i < 20; ++i) {
            log.info("Height: " + CharacterGeneratorService.randomHeight(character));
        }
    }

    /**
     * Test distrubution of DnD's method of random by testing it out on height (in inches)
     * 56 inches base + 2d10
     */
    @Test
    public void dndRandomTest() {
        long sum = 0;
        int[] distribution = new int[77];

        for (int i = 0; i < 1000; ++i) {
            int randomNumber = CharacterGeneratorService.dndRandom(56, 10, 10);
            sum += randomNumber;
            ++distribution[randomNumber];
        }
        log.info("Sum: " + sum + " Average: " + (sum/(double)1000));
        for (int i = 56; i < distribution.length; ++i) {
            String output = "i=" + i + " or \t" +
                CharacterGeneratorService.inchesToFormattedHeight(i) +
                " \tHits: " + distribution[i] + " \t";
            for (int j = 0; j < distribution[i]/2; ++j) {
                output += "+";
            }
            log.info(output);
        }
    }


    // -------------- Random distribution tests ----------------

    public void plusMinusRandomDistributionTest() {
        long sum = 0;
        int[] distribution = new int[121];

        for (int i = 0; i < 1000; ++i) {
            int randomNumber = CharacterGeneratorService.plusMinusRandomBellCurve(100, 20);
            sum += randomNumber;
            ++distribution[randomNumber];
        }
        log.info("Sum: " + sum + " Average: " + (sum/(double)1000));
        for (int i = 80; i < distribution.length; ++i) {
            String output = "i=" + i + " \tHits: " + distribution[i] + " \t";
            for (int j = 0; j < distribution[i]/2; ++j) {
                output += "+";
            }
            log.info(output);
        }
    }

    public void averageOfTwoRandomDistributionTest() {
        long sum = 0;
        int[] distribution = new int[121];

        double repeatedTrials = 10000;
        for (int i = 0; i < repeatedTrials; ++i) {
            int randomNumber = CharacterGeneratorService.boundedRandomBellcurve(100, 20);
            sum += randomNumber;
            ++distribution[randomNumber];
        }
        log.info("Sum: " + sum + " Average: " + (sum/repeatedTrials));
        for (int i = 80; i < distribution.length; ++i) {
            String output = "i=" + i + " \tHits: " + distribution[i] + " \t";
            for (int j = 0; j < distribution[i]/10; ++j) {
                output += "+";
            }
            log.info(output);
        }
    }

    public void gaussianRandomDistributionTest2() {
        long sum = 0;
        int[] distribution = new int[201];

        for (int i = 0; i < 1000; ++i) {
            int randomNumber = CharacterGeneratorService.gaussianRandom(100, 20);
            sum += randomNumber;
            ++distribution[randomNumber];
        }
        log.info("Sum: " + sum + " Average: " + (sum/(double)1000));
        for (int i = 40; i < distribution.length; ++i) {
            String output = "i=" + i + " \tHits: " + distribution[i] + " \t";
            for (int j = 0; j < distribution[i]/2; ++j) {
                output += "+";
            }
            log.info(output);
        }
    }
}
