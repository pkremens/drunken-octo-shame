/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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

package org.jboss.qe.syslog;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class PortBlockingHelper {

    public static void main(String[] args) throws IOException {
        PortBlocker blocker = new PortBlocker(9998);
        blocker.start();
    }

    private static class PortBlocker extends Thread {
        private final Logger log = Logger.getLogger(PortBlocker.class.getSimpleName());
        private ServerSocket listener;

        private PortBlocker() throws IOException {
            this.listener = new ServerSocket(8080);
        }

        private PortBlocker(int port) throws IOException {
            this.listener = new ServerSocket(port);
        }

        @Override
        public void run() {
            try {
                listener.accept();
            } catch (IOException e) {
                log.log(Level.WARNING, e.getLocalizedMessage(), e);
                try {
                    listener.close();
                } catch (IOException e1) {
                    log.log(Level.WARNING, e1.getLocalizedMessage(), e1);
                }
            }
        }

        public void closeConnection() throws IOException {
            // listener could be already closed
            if (!listener.isClosed())
                listener.close();
        }
    }
}
