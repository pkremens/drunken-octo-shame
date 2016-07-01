package cz.hradek.kremilek.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com on 29/04/15.
 */
public class LoggerFactory {
    public static Logger getLogger(String name) {
        Logger log = Logger.getLogger(name);
        log.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
        return log;
    }
}