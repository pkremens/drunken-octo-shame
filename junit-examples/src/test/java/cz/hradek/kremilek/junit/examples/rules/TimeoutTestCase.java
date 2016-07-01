package cz.hradek.kremilek.junit.examples.rules;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

/**
 * http://meri-stuff.blogspot.cz/2014/08/junit-rules.html
 * <p>
 * Timeout
 * The timeout rule can be used as both test rule and class rule. If it is declared as test rule, it applies the same
 * timeout limit to each test in the class. If it is declared as class rule, it applies the timeout limit to the whole
 * test case or test suite.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TimeoutTestCase {
    @Rule
    public TestRule methodTimeout = new DisableOnDebug(Timeout.seconds(1L));

    @ClassRule
    public static Timeout classTimeout = Timeout.seconds(3L);

    @Test
    public void shortOne() throws InterruptedException {
        Thread.sleep(200L);
    }

    @Test
    public void longOne() throws InterruptedException {
        Thread.sleep(1100L);
    }

    @Test
    public void longTwo() throws InterruptedException {
        Thread.sleep(1100L);
    }

    @Test
    public void half1() throws InterruptedException {
        Thread.sleep(500L);
    }

    @Test
    public void half2() throws InterruptedException {
        Thread.sleep(500L);
    }
}
