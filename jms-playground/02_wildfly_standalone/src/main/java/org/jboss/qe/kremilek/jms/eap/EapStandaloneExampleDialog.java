package org.jboss.qe.kremilek.jms.eap;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Start the EAP using the standalone-full(-ha).xml profile
 * <p>
 * Run in CLI:
 * /subsystem=messaging-activemq/server=default:write-attribute(name=security-enabled,value=false)
 * to disable authentication of remote JMS clients
 * <p>
 * Run in CLI:
 * jms-queue add --queue-address=testQueue --entries=queue/test,java:jboss/exported/jms/queue/test
 * to manually create a testQueue
 * jms-queue remove --queue-address=testQueue
 * to remove it on finish
 * <p>
 * Execute the EapStandaloneExampleDialog.
 * WARNING : At the moment, the queue could contain old unconsumed messages!
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class EapStandaloneExampleDialog extends JDialog {
    private int count = 0;

    private static final Logger LOG = Logger.getLogger(EapStandaloneExampleDialog.class.getName());
    private JMSContext jmsContext;
    private JMSConsumer jmsConsumer;
    private JMSProducer jmsProducer;
    private Destination jmsQueue;

    private JPanel contentPane;
    private JTextArea textArea1;
    private JButton sendButton;
    private JButton receiveButton;
    private JTextField countField;

    public EapStandaloneExampleDialog(String title) {
        this.setTitle(title);

        try {
            // Gets the JNDI context - use naming setup from jndi.properties
            InitialContext jndiContext = new InitialContext();
            // Lookup the RemoteConnectionFactory on Wildfly server
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/RemoteConnectionFactory");
            jmsQueue = (Destination) jndiContext.lookup("jms/queue/test");
            jmsContext = connectionFactory.createContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        jmsProducer = jmsContext.createProducer();
        jmsConsumer = jmsContext.createConsumer(jmsQueue);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(sendButton);

        sendButton.addActionListener(e -> onSend());

        receiveButton.addActionListener(e -> onReceive());
        receiveButton.setEnabled(false);

        countField.setText(String.valueOf(count));

        // call onReceive() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onReceive() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSend() {
        LOG.info("Sent a message to the " + jmsQueue.toString());
        jmsProducer.send(jmsQueue, String.format("%s: Hej hou from %s", new Date(), jmsQueue.toString()));
        count++;
        countField.setText(String.valueOf(count));
        receiveButton.setEnabled(true);
    }

    private void onReceive() {
        LOG.info("Receive a message from the " + jmsQueue.toString());
        count--;
        countField.setText(String.valueOf(count));
        if (count < 1) {
            receiveButton.setEnabled(false);
        }
        String message = jmsConsumer.receiveBody(String.class);
        textArea1.append(message);

        textArea1.append(System.lineSeparator());
    }

    private void onCancel() {
        dispose();
        jmsContext.close();
    }

    public static void main(String[] args) {
        EapStandaloneExampleDialog dialog = new EapStandaloneExampleDialog("EAP standalone client example");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
