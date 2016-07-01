package cz.hradek.kremilek.junit.examples.ordered.suite;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExcludedOneTestCase.class,
        ExcludedTwoTestCase.class
})
public class KremilekTestSuite {
    private static final Logger log = LoggerFactory.getLogger(KremilekTestSuite.class.getSimpleName());

    {
        log.info("Static block in suite - never happens");
    }

    @BeforeClass
    public static void beforeClassSuite() {
        log.info("beforeClassSuite");
    }

    @AfterClass
    public static void afterClassSuite() {
        log.info("afterClassSuite");
    }

    @Before
    public void beforeSuite() {
        log.info("beforeSuite");
        throw new RuntimeException("This never happens");
    }

    @After
    public void afterSuite() {
        log.info("afterSuite");
        throw new RuntimeException("This never happens");
    }

    @Test
    public void isItPossibleHere() {
        log.info("test in suite?");
        throw new RuntimeException("This never happens");
    }
}
