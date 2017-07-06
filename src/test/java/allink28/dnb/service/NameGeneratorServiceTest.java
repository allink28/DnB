package allink28.dnb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Allink on 7/5/2017.
 */
public class NameGeneratorServiceTest {

    private final Logger log = LoggerFactory.getLogger(NameGeneratorServiceTest.class);

    @Test
    public void generateMaleNameTest() {
        StringBuilder logOutput = new StringBuilder("\n");
        for (int i = 0; i < 50; ++i) {
            String name = NameGeneratorService.generateMaleName();
            assertTrue("Name: " + name + " is not correct.",
                !name.isEmpty() && java.lang.Character.isUpperCase(name.charAt(0)));
            logOutput.append(name).append(" ");
        }
        log.info(logOutput.toString());
    }


    @Test
    public void generateFemaleNameTest() {
        StringBuilder logOutput = new StringBuilder("\n");
        for (int i = 0; i < 50; ++i) {
            String name = NameGeneratorService.generateFemaleName();
            assertTrue("Name: " + name + " is not correct.",
                !name.isEmpty() && java.lang.Character.isUpperCase(name.charAt(0)));
            logOutput.append(name).append(" ");
        }
        log.info(logOutput.toString());
    }

}
