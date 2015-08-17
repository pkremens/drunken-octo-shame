package org.jboss.qa.test;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.client.helpers.Operations;
import org.jboss.dmr.ModelNode;
import org.jboss.qa.CLITestBase;
import org.junit.Test;

public class ControllerClientTestCase extends CLITestBase {
    private static ModelControllerClient client;

    /*
     * Reproducer from https://issues.jboss.org/browse/WFCORE-736
     */
    @Test
    public void testLeak() throws Exception {
        for (int i = 0; i < 100000; i++) {
            client = null;
            client = ModelControllerClient.Factory.create("localhost", PORT);
            ModelNode operation = Operations.createReadAttributeOperation(new ModelNode().setEmptyList(), "server-state");
            try {
                client.execute(operation);
                client.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            if (i % 1000 == 0) {
                System.out.println("Processed: " + i);
            }
        }
    }
}
