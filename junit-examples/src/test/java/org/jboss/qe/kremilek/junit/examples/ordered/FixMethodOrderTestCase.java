package org.jboss.qe.kremilek.junit.examples.ordered;

import org.jboss.qe.kremilek.junit.examples.ordered.suite.ExcludedOneTestCase;
import org.jboss.qe.kremilek.logging.LoggerFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixMethodOrderTestCase {
    private static final Logger log = LoggerFactory.getLogger(ExcludedOneTestCase.class.getSimpleName());

    @Test
    public void aaa() {
        log.info("I am first");
    }

    @Test
    public void bbb() {
        log.info("I am second");
    }

    @Test
    public void ccc() {
        log.info("I am third");
    }

    @Test
    public void ddd() {
        log.info("I am fourth");
    }

    @Test
    public void eee() {
        log.info("I am fifth");
    }

    @Test
    public void fff() {
        log.info("I am sixth");
    }

}
