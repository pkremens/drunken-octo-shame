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

package oeg.jboss.qe.management.client;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class ReloadTestCase {
    @Test
    public void simpleUsageTest() throws IOException {
        ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);
        ModelNode op = new ModelNode();
        op.get("operation").set("reload");
        System.out.println(op.asString());
        ModelNode result = client.execute(op);
        System.out.println(result.asString());

    }
}
