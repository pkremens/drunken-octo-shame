/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.qe.kremilek.jms.artemis;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.remoting.impl.invm.InVMConnectorFactory;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.jboss.qe.kremilek.jms.artemis.OrderingDemo.orderingDemo;

/**
 * This example demonstrates how to run an embedded ActiveMQ Artemis broker with programmatic configuration
 */
public final class EmbeddedExample {

    public static void main(final String[] args) throws Exception {
        // Step 1. Configure and start the embedded broker.
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm", "vm://0"));
        server.start();

        embeddedJndiTest();
        embeddedDirectInstantiating();

        server.stop();
        // This one keeps the jvm running due to some running threads
    }

    /**
     * Instantiating JMS Resources using JNDI
     */
    private static void embeddedJndiTest() throws NamingException, JMSException {
        InitialContext ic = new InitialContext();

        // Now we'll look up the connection factory from which we can create connections to embedded broker:
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("EmbeddedConnectionFactory");
        // And look up the Queue:
        Queue orderQueue = (Queue) ic.lookup("queues/OrderQueue");
        orderingDemo(cf, orderQueue, "JMS Resources using JNDI");
    }

    /**
     * Directly instantiating JMS Resources without using JNDI
     */
    private static void embeddedDirectInstantiating() throws JMSException {
        TransportConfiguration transportConfiguration = new TransportConfiguration(InVMConnectorFactory.class.getName());
        ConnectionFactory cf = ActiveMQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        // We also create the JMS Queue object via the ActiveMQJMSClient Utility class:
        Queue orderQueue = ActiveMQJMSClient.createQueue("OrderQueue");
        orderingDemo(cf, orderQueue, "JMS Resources without using JNDI");
    }
}
