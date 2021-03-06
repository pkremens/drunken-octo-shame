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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class UDPSyslogListener {

    public static void main(String[] args) {
        UdpListener worker = new UdpListener(9998);
        worker.start();
    }

    private static class UdpListener extends Thread {
        private final Logger log = Logger.getLogger(UdpListener.class.getSimpleName());
        private final int localport;
        private DatagramSocket socket;
        private final StringBuilder sb = new StringBuilder();

        public UdpListener(int localport) {
            this.localport = localport;
        }

        @Override
        public void run() {
            try {
                log.info(String.format("Starting a simple UDP syslog server listening on port %d", localport));
                this.socket = new DatagramSocket(localport);
                while (true) {
                    sb.setLength(0);
                    DatagramPacket data = new DatagramPacket(new byte[2048], 2048);
                    socket.receive(data);
                    String load = new String(data.getData(), 0, data.getLength());
                    log.info(load);
                }

            } catch (Exception e) {
                log.log(Level.WARNING, "Failed to receive datagram.", e);
            } finally {
                if (socket != null) {
                    this.socket.close();
                }
            }
        }
    }
}
