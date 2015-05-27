/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 *
 */
package org.jboss.logging.example;

import org.jboss.logging.*;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.Message.Format;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * @author James R. Perkins Jr. (jrp)
 */
@MessageLogger(projectCode = "LOGL")
interface DefaultLogger {

    /**
     * The default logger.
     */
    DefaultLogger LOGGER = Logger.getMessageLogger(DefaultLogger.class, DefaultLogger.class.getPackage().getName());

    @LogMessage(level = Level.FATAL)
    @Message(value = "Cannot process %s.")
    void hardStop(Class<? extends Annotation> annotation);

    @LogMessage(level = Level.WARN)
    @Message(id = 1, value = "%s cannot be null.")
    void valueNotNull(String value);

    @LogMessage(level = Level.ERROR)
    void valueNotNull(@Cause IllegalArgumentException cause, Object value);

    @LogMessage(level = Level.ERROR)
    @Message("Value %s of cannot be null. Message: %s")
    void valueNotNull(@Cause IllegalArgumentException cause, Object value, String message);

    @LogMessage(level = Level.INFO)
    @Message(id = 2, value = "Version: %s")
    void version(String version);

    @LogMessage(level = Level.FATAL)
    @Message(id = 32, value = "This is not good! %s is melting.")
    void meltDown(@Cause Throwable cause, String value);

    @Message(id = 4, value = "Value {0} could not be added to {1}",
            format = Format.MESSAGE_FORMAT)
    IllegalArgumentException illegalValue(@Cause Throwable cause, String value, String collection);

    @Message(id = 5, value = "Value is invalid.",
            format = Format.MESSAGE_FORMAT)
    String invalidValue();

    @Message(id = 99, value = "An illegal argument was found.")
    IllegalArgumentException illegalArgument();

    @LogMessage(level = Logger.Level.INFO)
    @Message(id=3, value="Customer query failed, user:%2$s, customerid:%1$s")
    void customerLookupFailed(Long customerid, String username);

    @LogMessage(level = Level.FATAL)
    @Message(value = "Cannot process %s.")
    void hardSxtop(Class<? extends Annotation> annotation);
}
