package cz.hradek.kremilek.junit.examples.rules.custom;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class ExternalResourceTestCase {
    private static Logger log = LoggerFactory.getLogger(ExternalResourceTestCase.class.getSimpleName());

    // @ClassRule
    // public static LoggingExternalResourceRule loggerResource = new LoggingExternalResourceRule();
/*
01/07/2016 07:10:44.851 - [LoggingExternalResourceRule.before] [INFO] before rule
01/07/2016 07:10:44.853 - [ExternalResourceTestCase.beforeClass] [INFO] before class test
01/07/2016 07:10:44.854 - [ExternalResourceTestCase.before] [INFO] before test
01/07/2016 07:10:44.855 - [ExternalResourceTestCase.test] [INFO] test1
01/07/2016 07:10:44.855 - [LoggingExternalResourceRule.ruleLog] [INFO] Hello from LoggingExternalResourceRule
01/07/2016 07:10:44.855 - [ExternalResourceTestCase.test] [INFO] test2
01/07/2016 07:10:44.855 - [ExternalResourceTestCase.after] [INFO] after test
01/07/2016 07:10:44.856 - [ExternalResourceTestCase.afterClass] [INFO] after class test
01/07/2016 07:10:44.857 - [LoggingExternalResourceRule.after] [INFO] after rule
 */

    @Rule
    public LoggingExternalResourceRule loggerResource = new LoggingExternalResourceRule();
/*
01/07/2016 07:10:18.680 - [ExternalResourceTestCase.beforeClass] [INFO] before class test
01/07/2016 07:10:18.683 - [LoggingExternalResourceRule.before] [INFO] before rule
01/07/2016 07:10:18.683 - [ExternalResourceTestCase.before] [INFO] before test
01/07/2016 07:10:18.683 - [ExternalResourceTestCase.test] [INFO] test1
01/07/2016 07:10:18.684 - [LoggingExternalResourceRule.ruleLog] [INFO] Hello from LoggingExternalResourceRule
01/07/2016 07:10:18.684 - [ExternalResourceTestCase.test] [INFO] test2
01/07/2016 07:10:18.684 - [ExternalResourceTestCase.after] [INFO] after test
01/07/2016 07:10:18.686 - [LoggingExternalResourceRule.after] [INFO] after rule
01/07/2016 07:10:18.687 - [ExternalResourceTestCase.afterClass] [INFO] after class test
*/

    @BeforeClass
    public static void beforeClass() {
        log.info("before class test");
    }

    @AfterClass
    public static void afterClass() {
        log.info("after class test");
    }

    @Before
    public void before() {
        log.info("before test");
    }

    @After
    public void after() {
        log.info("after test");
    }

    @Test
    public void test() {
        log.info("test1");
        loggerResource.ruleLog();
        log.info("test2");
    }

}
