package org.jboss.qe.management.concurent.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class LoggerFactory {
    public static Logger getLogger(String name) {
        Logger log = Logger.getLogger(name);
        log.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        log.addHandler(handler);
        return log;
    }
}