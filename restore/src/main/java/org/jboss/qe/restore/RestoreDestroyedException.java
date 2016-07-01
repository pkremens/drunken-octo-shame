package org.jboss.qe.restore;

/**
 * Exception thrown by {@link Restore} in case that user is trying to perform operations on already destroyed instance.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class RestoreDestroyedException extends Exception {
    private static final String ALREADY_DESTROYED = "This Restore instance was already destroyed and cannot be reused. " +
            "A new Restore instance have to be initialized.";

    public RestoreDestroyedException() {
        super(ALREADY_DESTROYED);
    }


}
