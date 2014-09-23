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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class TCPSyslogListener {
    public static void main(String[] args) throws IOException {
        TCPListener server = new TCPListener(new ServerSocket(9998));
        server.start();
    }

    private static class TCPListener extends Thread {

        private final Logger log = Logger.getLogger(TCPListener.class.getSimpleName());
        private final ServerSocket serverSocket;
        private volatile Socket socket;
        private int control = -1;

        public TCPListener(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        Socket accept(ServerSocket serverSocket) throws IOException {
            return serverSocket.accept();
        }

        @Override
        public void run() {
            try {
                log.info(String.format("Starting a simple TCP syslog server listening on port %d", serverSocket.getLocalPort()));
                socket = accept(serverSocket);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                InputStream in = new BufferedInputStream(socket.getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int i = in.read();
                while (i != -1) {
                    out.write(i);
                    if (i == 123) {
                        control = (control == -1) ? 1 : control + 1;
                    }
                    if (i == 125) {
                        control--;
                    }

                    if (control == 0) {
                        // message ends with whitespace
                        i = in.read();
                        out.write(i);
                        control = -1;
                        log.info(out.toString());
                        out.reset();
                    }
                    i = in.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
