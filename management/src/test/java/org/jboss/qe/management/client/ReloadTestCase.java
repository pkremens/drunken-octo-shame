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

package org.jboss.qe.management.client;

import junit.framework.Assert;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.NAME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_ATTRIBUTE_OPERATION;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class ReloadTestCase {
    @Test
    public void simpleUsageTest() throws IOException, InterruptedException {

        String serverType = System.getProperty("server.type");
        if (serverType == null) {
            throw new RuntimeException("Failed to resolve server.type property");
        }
        int port = serverType.equals("eap") ? 9999 : 9990;
        System.out.println("Testing to reload " + serverType + " using management api.");

        ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), port);
        ModelNode op = new ModelNode();
        op.get("operation").set("reload");
        System.out.println("Executing: " + op.asString());
        ModelNode result = client.execute(op);
        System.out.println("Result: " + result.get("outcome").asString());

        // give some time as reload operation via mgmt api is not blocking
        Thread.sleep(1000);

        // wait for server to be ready again :read-attribute(name=server-state)
        op.get("operation").set(READ_ATTRIBUTE_OPERATION);
        op.get(NAME).set("server-state");

        boolean started = false;
        for (int i = 0; i < 10; i++) {
            try {
                result = client.execute(op);
            } catch (IOException ioe) {
                System.out.println("Wait for server to be ready");
                Thread.sleep(1000);
            }

            if (result.get("outcome").asString().equals("success")) {
                started = true;
                break;
            }
        }
        Assert.assertTrue("Failed to connect to the server after invoking restart via mgmt api.", started);
    }
}
