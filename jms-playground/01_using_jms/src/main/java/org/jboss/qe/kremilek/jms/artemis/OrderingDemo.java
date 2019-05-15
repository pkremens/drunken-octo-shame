package org.jboss.qe.kremilek.jms.artemis;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class OrderingDemo {


    public static void orderingDemo(ConnectionFactory cf, Queue orderQueue, String connectionOrigin) throws JMSException {
        // We create a JMS connection using the connection factory:
        Connection connection = cf.createConnection();

        // And we create a non transacted JMS Session, with AUTO_ACKNOWLEDGE acknowledge mode:
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // We create a MessageProducer that will send orders to the queue:
        MessageProducer producer = session.createProducer(orderQueue);

        // And we create a MessageConsumer which will consume orders from the queue:
        MessageConsumer consumer = session.createConsumer(orderQueue);

        // We make sure we start the connection, or delivery won't occur on it:
        connection.start();

        // We create a simple TextMessage and send it:
        TextMessage message = session.createTextMessage("This is an order " + connectionOrigin);
        producer.send(message);

        // And we consume the message:
        TextMessage receivedMessage = (TextMessage) consumer.receive();
        System.out.println("Got order: " + receivedMessage.getText());
        connection.close();
    }
}
