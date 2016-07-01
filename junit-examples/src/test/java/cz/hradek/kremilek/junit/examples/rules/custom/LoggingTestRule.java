package cz.hradek.kremilek.junit.examples.rules.custom;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class LoggingTestRule implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(LoggingTestRule.class.getSimpleName());


    public Statement apply(Statement base, Description description) {
        return statement(base);
    }

    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                log.info("Do something here.");
                try {
                    base.evaluate();
                } finally {
                    log.info("Always do something there.");
                }
            }
        };
    }

}
