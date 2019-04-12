package cz.hradek.kremilek.junit.examples.soft;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class SoftAssertionsTestCase {
    @Rule
    public JUnitSoftAssertions softAssertions = new JUnitSoftAssertions();

    @Test
    public void makeAssertionsOnSomeString() {
        String fails = "Sorry, I've got to fail!";

        softAssertions.assertThat(fails)
                .as("Have size")
                .hasSize(8);
        softAssertions.assertThat(fails)
                .as("Start with")
                .startsWith("Congrats,");
        softAssertions.assertThat(fails)
                .as("Doesn't contain \"fail\" string")
                .doesNotContain("fail");
    }
}
