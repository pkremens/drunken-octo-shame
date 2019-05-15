package org.jboss.qe.kremilek.jms.artemis;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.jboss.qe.kremilek.jms.artemis.OrderingDemo.orderingDemo;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class BrokerExample {
    public static void main(String[] args) throws JMSException, NamingException {
        // start the JMS broker here before running this example
        brokerJndiTest();
        brokerDirectInstantiating();
    }

    /**
     * Instantiating JMS Resources using JNDI
     */
    private static void brokerJndiTest() throws NamingException, JMSException {
        InitialContext ic = new InitialContext();
        //Now we'll look up the connection factory from which we can create connections to localhost:61616:
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("ConnectionFactory");
        //And look up the Queue:
        Queue orderQueue = (Queue) ic.lookup("queues/OrderQueue");
        orderingDemo(cf, orderQueue, "JMS Resources using JNDI");
    }

    /**
     * Directly instantiating JMS Resources without using JNDI
     */
    private static void brokerDirectInstantiating() throws JMSException {
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());
        ConnectionFactory cf = ActiveMQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        //We also create the JMS Queue object via the ActiveMQJMSClient Utility class:
        Queue orderQueue = ActiveMQJMSClient.createQueue("OrderQueue");
        orderingDemo(cf, orderQueue, "JMS Resources without using JNDI");
    }
}
