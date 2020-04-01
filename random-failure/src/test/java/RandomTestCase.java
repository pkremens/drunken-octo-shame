import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class RandomTestCase {

    private static final Logger LOGGER = Logger.getLogger(RandomTestCase.class.getName());
    private static String FAILURE_PROBABILITY_PROPERTY = "failure.probability";
    private static final int FAILURE_PROBABILITY_DEFAULT = 50;
    private static int failureProbability;

    @BeforeClass
    public static void setupProbability() {
        LOGGER.info(FAILURE_PROBABILITY_PROPERTY + "=" + System.getProperty(FAILURE_PROBABILITY_PROPERTY));
        int probability = Integer.getInteger(FAILURE_PROBABILITY_PROPERTY, FAILURE_PROBABILITY_DEFAULT);
        if (probability > 100 || probability < 0) {
            LOGGER.info("Failure probability has to be set between 0-100, fallback to " + FAILURE_PROBABILITY_DEFAULT);
            failureProbability = FAILURE_PROBABILITY_DEFAULT;
        } else {
            LOGGER.info("Failure probability set to " + probability);
            failureProbability = probability;
        }
    }


    @Test
    public void randomTest() {
//        int pass = 0;
//        int fail = 0;
//        for (int i = 0; i < 100000; i++) {
//            int random = new Random().ints(0, 100).limit(1).findFirst().getAsInt();
////            LOGGER.info("Failure threshold: " + failureProbability + " RANDOM: " + random);
////            LOGGER.info("PASS: " + (failureProbability <= random));
//            if (failureProbability <= random) {
//                pass++;
//            } else {
//                fail++;
//            }
//        }
//        LOGGER.info("");
//        LOGGER.info("Passed: " + pass);
//        LOGGER.info("Failed: " + fail);

        int random = new Random().ints(0, 100).limit(1).findFirst().getAsInt();
        LOGGER.info("Assert that failure threshold: " + failureProbability + " < random (0-99): " + random);
        Assert.assertTrue((failureProbability < random));
    }
}
