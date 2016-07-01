package org.jboss.qe.kremilek.junit.examples.ordered.suite;


import org.jboss.qe.kremilek.logging.LoggerFactory;
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
public class ExcludedOneTestCase {
    private static final Logger log = LoggerFactory.getLogger(ExcludedOneTestCase.class.getSimpleName());

    {
        log.info("static block in ExcludedOneTestCase");
    }

    @BeforeClass
    public static void beforeClass() {
        log.info("Before class");
    }

    @Before
    public void before() {
        log.info("Before");
    }

    @After
    public void after() {
        log.info("After");
    }

    @AfterClass
    public static void afterClass() {
        log.info("After class");
    }

    @Test
    public void excludedOneFirst() {
        log.info("excludedOneFirst");
    }

    @Test
    public void excludedOneSecond() {
        log.info("excludedOneSecond");
    }

    @Test
    public void excludedOneThird() {
        log.info("excludedOneThird");
    }
}
