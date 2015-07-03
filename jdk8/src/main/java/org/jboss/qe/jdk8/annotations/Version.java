package org.jboss.qe.jdk8.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {
    int major();

    int minor() default 0;
}
