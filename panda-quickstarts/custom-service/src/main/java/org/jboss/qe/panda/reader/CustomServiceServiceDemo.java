package org.jboss.qe.panda.reader;

import org.jboss.qa.eap.panda.Main;
import org.jboss.qa.eap.panda.configuration.Configuration;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class CustomServiceServiceDemo {
    public static void main(String[] args) {
        final Main panda = new Main();
        final Configuration configuration = panda.getConfiguration();
        configuration.printHelp();
    }
}
