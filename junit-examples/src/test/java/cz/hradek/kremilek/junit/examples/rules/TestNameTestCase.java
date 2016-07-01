package cz.hradek.kremilek.junit.examples.rules;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.logging.Logger;

/**
 * See {@link LogTestNameTestCase} for quite different (not so elegant) implementation.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TestNameTestCase {
    private static final Logger log = LoggerFactory.getLogger(TestNameTestCase.class.getSimpleName());

    @Rule
    public TestName name = new TestName();

    @Test
    public void test1() {
        log.info(name.getMethodName());
    }
}
