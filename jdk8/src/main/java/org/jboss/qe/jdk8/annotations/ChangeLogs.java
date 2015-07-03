package org.jboss.qe.jdk8.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeLogs {
    ChangeLog[] value();
}