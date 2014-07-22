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

package test;

/**
 * https://community.jboss.org/thread/222734?start=0&tstart=0
 * https://bugzilla.redhat.com/show_bug.cgi?id=1020510
 *
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class CCTest {
    // Add jboss cli client to classpath first
//    public static void main(String[] args) throws CommandLineException {
//        String host = "localhost";
//        int port = 9999;
//        CommandContext ctx = CommandContextFactory.getInstance().newCommandContext();
//        ctx.connectController(host, port);
////        ctx.handle("shutdown");
////        ModelNode result = ctx.buildRequest("shutdown");
////        System.out.println(result.asString());
//        String line = "data-source add --name=jbpmDS --jndi-name=java:jboss/datasources/jbpmDS --driver-name=h2 --user-name=soa --password=\"soa\" --connection-url=\"jdbc:h2:file:${jboss.server.data.dir}/ô/h2/soa;mvcc=true\"";
//        ctx.handle(line);
//        ctx.terminateSession();
//        ctx.disconnectController();
//    }
}
