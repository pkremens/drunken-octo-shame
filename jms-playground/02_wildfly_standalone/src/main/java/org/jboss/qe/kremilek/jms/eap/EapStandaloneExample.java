package org.jboss.qe.kremilek.jms.eap;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Run in CLI:
 * /subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)
 * to disable authentication of remote JMS clients
 * <p>
 * Run in CLI:
 * jms-queue add --queue-address=testQueue --entries=queue/test,java:jboss/exported/jms/queue/test
 * to manually create a testQueue
 * jms-queue remove --queue-address=testQueue
 * to remove it on finish
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class EapStandaloneExample {
    public static void main(String[] args) throws InterruptedException {
        try {
            // Gets the JNDI context - use naming setup from jndi.properties
            InitialContext jndiContext = new InitialContext();
            // Lookup the RemoteConnectionFactory on Wildfly server
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/RemoteConnectionFactory");
            Destination queue = (Destination) jndiContext.lookup("jms/queue/test");

            // Sent a message to the queue
            try (JMSContext context = connectionFactory.createContext()) {
                context.createProducer().send(queue, "Hej hou from " + queue.toString());
                Thread.sleep(100L);
                System.out.println(context.createConsumer(queue).receiveBody(String.class));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
