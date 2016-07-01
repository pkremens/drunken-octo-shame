package cz.hradek.kremilek.junit.examples.rules;

/**
 * http://meri-stuff.blogspot.cz/2014/08/junit-rules.html#UsingRulesExampleRuleTemporaryFolder
 * <p>
 * Expected exception runs the test and catches any exception it throws. The rule is able to check whether the exception
 * contains the right message, the right cause and whether it was thrown by the right line.
 * <p>
 * Expected exception has private constructor and must be initialized using static none method. Each exception throwing
 * test has to configure expected exception parameters and then call the expect method of the rule. The rule fails if:
 * - the test throws any exception before the expect method call,
 * - the test does not throw an exception after the expect method call,
 * - thrown exception does not have the right message, class or cause.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class ExpectedExceptionTestCase {
}
