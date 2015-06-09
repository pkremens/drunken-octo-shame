package org.jboss.qa.utils;

/**
 * @author Petr Kremensky pkremens@redhat.com on 30/04/2015
 */
public class CLITestsuiteException extends RuntimeException {
    public CLITestsuiteException() {
        super();
    }

    public CLITestsuiteException(String message) {
        super(message);
    }
}
