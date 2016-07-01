package cz.hradek.kremilek.junit.examples.rules.custom;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TestRuleTestCase {
    @Rule
    public TestRule myRule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        // tohle normalne pusti test
                        base.evaluate();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    } finally {
                        // pseudo after
                    }
                    description.toString();
                }
            };
        }
    };

    @Test
    public void nevidimNaTO() {
        System.out.println("Slunko pitome, vlak pitomy");
    }


}
