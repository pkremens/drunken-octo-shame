package org.jboss.qe.jgit.example;

/**
 * @author Petr Kremensky pkremens@redhat.com on 29/06/2016
 */
public class RestoreException extends Exception {
    public RestoreException(String message) {
        super(message);
    }

    public RestoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
