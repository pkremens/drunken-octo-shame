package cz.hradek.kremilek.junit.examples.rules.custom;

import cz.hradek.kremilek.logging.LoggerFactory;
import org.junit.rules.ExternalResource;

import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class LoggingExternalResourceRule extends ExternalResource {
    private static final Logger log = LoggerFactory.getLogger(LoggingExternalResourceRule.class.getSimpleName());

    public void ruleLog() {
        log.info(getHello() + " from " + this.getClass().getSimpleName());
    }

    private String getHello() {
        return "Hello";
    }

    @Override
    public void before() {
        log.info("before rule");
    }

    @Override
    public void after() {
        log.info("after rule");
    }

}
