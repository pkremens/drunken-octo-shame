package org.jboss.qe.restore;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class RestoreException extends Exception {
    public RestoreException(String message) {
        super(message);
    }

    public RestoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
