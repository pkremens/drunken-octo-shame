package org.jboss.qe.kremilek.jms.api;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class ClasicDemo {
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
            Connection connection = null;
            try {
                InitialContext ic = new InitialContext();
                // Now we'll look up the connection factory from which we can create connections to embedded broker:
                ConnectionFactory cf = (ConnectionFactory) ic.lookup("EmbeddedConnectionFactory");

                // And look up the Queue:
                Queue orderQueue = (Queue) ic.lookup("queues/OrderQueue");

                // We create a JMS connection using the connection factory:
                connection = cf.createConnection();

                // And we create a non transacted JMS Session, with AUTO_ACKNOWLEDGE acknowledge mode:
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // We create a MessageProducer that will send orders to the queue:
                MessageProducer producer = session.createProducer(orderQueue);

                // We make sure we start the connection, or delivery won't occur on it:
                connection.start();

                for (int i = 0; i < 5; i++) {
                    System.out.println("Send a message id: " + i);
                    // We create a simple TextMessage and send it:
                    TextMessage message = session.createTextMessage("This is an order " + i);
                    producer.send(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class MyConsumer implements Runnable {
        public void run() {
            Connection connection = null;
            try {
                InitialContext ic = new InitialContext();
                // Now we'll look up the connection factory from which we can create connections to embedded broker:
                ConnectionFactory cf = (ConnectionFactory) ic.lookup("EmbeddedConnectionFactory");

                // And look up the Queue:
                Queue orderQueue = (Queue) ic.lookup("queues/OrderQueue");

                // We create a JMS connection using the connection factory:
                connection = cf.createConnection();

                // And we create a non transacted JMS Session, with AUTO_ACKNOWLEDGE acknowledge mode:
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // We create a MessageProducer that will send orders to the queue:
                MessageConsumer consumer = session.createConsumer(orderQueue);

                // We make sure we start the connection, or delivery won't occur on it:
                connection.start();

                for (int i = 0; i < 5; i++) {
                    TextMessage receivedMessage = (TextMessage) consumer.receive();
                    System.out.println("Got order: " + receivedMessage.getText());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
