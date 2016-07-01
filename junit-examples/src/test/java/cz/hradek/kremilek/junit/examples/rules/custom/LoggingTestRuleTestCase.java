package cz.hradek.kremilek.junit.examples.rules.custom;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class LoggingTestRuleTestCase {
    private static final Logger log = LoggerFactory.getLogger(LoggingTestRuleTestCase.class.getSimpleName());

    @Rule
    public LoggingTestRule loggingRule = new LoggingTestRule();


    @Test
    public void doIt() {
        log.info("Just do it.");
    }
}
