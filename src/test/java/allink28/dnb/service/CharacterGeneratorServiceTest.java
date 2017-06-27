package allink28.dnb.service;

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
                !name.isEmpty() && Character.isUpperCase(name.charAt(0)));
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
                !race.isEmpty() && Character.isUpperCase(race.charAt(0)));
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
                !aClass.isEmpty() && Character.isUpperCase(aClass.charAt(0)));
            logOutput.append(aClass).append(" ");
        }
        log.info(logOutput.toString());
    }
}
