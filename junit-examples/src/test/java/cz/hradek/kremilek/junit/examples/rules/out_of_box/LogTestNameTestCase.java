package cz.hradek.kremilek.junit.examples.rules.out_of_box;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.logging.Logger;

/**
 * See {@link TestNameTestCase} for much more simple implementation.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class LogTestNameTestCase {
    private static final Logger log = LoggerFactory.getLogger(LogTestNameTestCase.class.getSimpleName());


    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("Starting test: " + description.getMethodName() + "()");
        }
    };

    @Test
    public void foo() {
        log.info("bar");
    }

    @Test
    public void bar() {
        log.info("foo");
    }
}
