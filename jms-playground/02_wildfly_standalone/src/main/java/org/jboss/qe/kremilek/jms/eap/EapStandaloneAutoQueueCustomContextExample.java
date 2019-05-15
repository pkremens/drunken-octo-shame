package org.jboss.qe.kremilek.jms.eap;

import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class EapStandaloneAutoQueueCustomContextExample {
    private static final Logger log = Logger.getLogger(EapStandaloneAutoQueueCustomContextExample.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/pkremens";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

    public static void main(String[] args) {

        Context namingContext = null;

        try {
            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            /*
             * We want to artemis create a queue by itself in here so JNDI is not possible (the target
             * destination does not exist yet).
             */
//            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
//            log.info("Attempting to acquire destination \"" + destinationString + "\"");
//            Destination destination = (Destination) namingContext.lookup(destinationString);
//            log.info("Found destination \"" + destinationString + "\" in JNDI");

            /**
             * Run in CLI:
             * /subsystem=messaging-activemq/server=default/address-setting=#:write-attribute(name=auto-create-queues, value=true)
             * to allow artemis creating a queues on demand
             */
            log.info("Create a queue programmatically");
            Destination destination = ActiveMQDestination.createQueue(DEFAULT_DESTINATION);

            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
            String content = System.getProperty("message.content", DEFAULT_MESSAGE);
            /**
             * Run in CLI:
             * /subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)
             * to disable authentication of remote JMS clients
             */
            try (JMSContext context = connectionFactory.createContext()) {
                log.info("Sending " + count + " messages with content: " + content);
                // Send the specified number of messages
                for (int i = 0; i < count; i++) {
                    context.createProducer().send(destination, content + " " + ((ActiveMQQueue) destination).getQueueName());
                }

                // Create the JMS consumer
                JMSConsumer consumer = context.createConsumer(destination);
                // Then receive the same number of messages that were sent
                for (int i = 0; i < count; i++) {
                    String text = consumer.receiveBody(String.class, 5000);
                    log.info("Received message with content " + text);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
            log.warning(e.getMessage());
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                    log.warning(e.getMessage());
                }
            }
        }
    }
}
