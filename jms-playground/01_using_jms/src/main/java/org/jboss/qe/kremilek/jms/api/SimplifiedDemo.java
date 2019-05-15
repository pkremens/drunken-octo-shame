package org.jboss.qe.kremilek.jms.api;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class SimplifiedDemo {
    public static void main(String[] args) throws Exception {
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm", "vm://0"));
        // start the server
        server.start();

        // run the producer thread
        Thread producerThread = new Thread(new MyProducer());
        producerThread.start();

        // run the consumer thread
        Thread consumerThread = new Thread(new MyConsumer());
        consumerThread.start();

        // wait for threads to finish the work
        producerThread.join();
        consumerThread.join();

        // stop the server
        server.stop();
    }


    private static class MyProducer implements Runnable {
        public void run() {
            try {
                // Get the JNDI context:
                InitialContext ic = new InitialContext();

                // Now we'll look up the connection factory from which we can create connections to embedded broker:
                ConnectionFactory cf = (ConnectionFactory) ic.lookup("EmbeddedConnectionFactory");

                // And look up the Destination:
                Destination orderQueue = (Destination) ic.lookup("queues/OrderQueue");

                // Send a text message to the queue
                try (JMSContext context = cf.createContext()) {
                    JMSProducer producer = context.createProducer();
                    for (int i = 0; i < 5; i++) {
                        System.out.println("Send a message id: " + i);
                        // We create a simple TextMessage and send it:
                        producer.send(orderQueue, "This is an order " + i + " sent at " + new Date());
                    }
                }
            } catch (NamingException nex) {
                nex.printStackTrace();
            }
            System.out.println("Exiting the producer");
        }
    }

    private static class MyConsumer implements Runnable {
        public void run() {
            try {
                // Lookup a JNDI context
                InitialContext ic = new InitialContext();

                // Now we'll look up the connection factory from which we can create connections to embedded broker:
                ConnectionFactory cf = (ConnectionFactory) ic.lookup("EmbeddedConnectionFactory");

                // And look up the Destination:
                Destination orderQueue = (Destination) ic.lookup("queues/OrderQueue");

                // Loop to receive the messages:
                try (JMSContext context = cf.createContext()) {
                    JMSConsumer consumer = context.createConsumer(orderQueue);
//                    while (true) {
                    for (int i = 0; i < 5; i++) {
                        String message = consumer.receiveBody(String.class);
                        System.out.println("Got order: " + message);
                    }
                }
            } catch (NamingException nex) {
                nex.printStackTrace();
            }
            System.out.println("Exiting the consumer");
        }
    }
}
