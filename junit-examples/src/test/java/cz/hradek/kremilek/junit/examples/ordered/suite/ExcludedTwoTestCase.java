package cz.hradek.kremilek.junit.examples.ordered.suite;


import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.logging.Logger;


/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExcludedTwoTestCase {
    private static final Logger log = LoggerFactory.getLogger(ExcludedTwoTestCase.class.getSimpleName());

    {
        log.info("static block in ExcludedTwoTestCase");
    }

    @BeforeClass
    public static void beforeClass() {
        log.info("Before class");
    }

    @AfterClass
    public static void afterClass() {
        log.info("After class");
    }

    @Before
    public void before() {
        log.info("Before");
    }

    @After
    public void after() {
        log.info("After");
    }

    @Test
    public void excludedTwoFirst() {
        log.info("excludedTwoFirst");
    }

    @Test
    public void excludedTwoSecond() {
        log.info("excludedTwoSecond");
    }

    @Test
    public void excludedTwoThird() {
        log.info("excludedTwoThird");
    }
}
