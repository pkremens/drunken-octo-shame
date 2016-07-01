package cz.hradek.kremilek.junit.examples.rules;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TimeoutMethodTestCase {
    @Rule
    public Timeout methodTimeout = Timeout.seconds(1L);

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
