package org.jboss.qa.test;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.as.controller.client.helpers.Operations;
import org.jboss.dmr.ModelNode;
import org.jboss.qa.CLITestBase;
import org.junit.Test;

/**
 * https://issues.jboss.org/browse/WFCORE-736
 */
public class ControllerClientTestCase extends CLITestBase {
    private static ModelControllerClient client;

    @Test
    public void testLeak() throws Exception{
        for (int i = 0; i < 1000000; i++) {
            client = null;
            client = ModelControllerClient.Factory.create("localhost", 9990);
            ModelNode operation = Operations.createReadAttributeOperation(new ModelNode().setEmptyList(), "server-state");
            client.execute(operation);
            client.close();
            if (i % 1000 == 0) {
                System.out.println("Processed: " + i);
            }
        }
    }
}
