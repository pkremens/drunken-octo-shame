package cz.hradek.kremilek.junit.examples.rules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

/**
 * http://meri-stuff.blogspot.cz/2014/08/junit-rules.html
 * <p>
 * Error Collector
 * Error collector allows you to run multiple checks inside the test and then report all their failures at once after
 * the test ends.
 * <p>
 * Expected-vs-actual value assertions are evaluated using the checkThat method exposed by the rule. It accepts
 * hamcrest matcher as an argument and thus can be used to check anything.
 * <p>
 * Unexpected exceptions can be reported directly using addError(Throwable error) method. Alternatively, if you
 * have an instance of Callable to be run, you can call it through checkSucceeds method which adds any thrown
 * exception into errors list.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class ErrorCollectorTestCase {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void collectorTest() {
        final String redWings = "Yzerman Shanahan Fedorov Lidstron";
        collector.checkThat(redWings, not(containsString("Bure")));
        collector.checkThat(redWings, containsString("Hull"));
        collector.checkThat(redWings, containsString("Chelios"));
    }

}
