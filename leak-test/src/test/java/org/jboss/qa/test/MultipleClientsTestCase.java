package org.jboss.qa.test;

import org.jboss.qa.CLITestBase;
import org.jboss.qa.worker.CLISingleRequestWorker;
import org.jboss.qa.worker.CLITestExecutor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Run a hundreds of :read-attribute(name=server-state) requests via jboss-cli.sh script using multiple clients. Monitor
 * server resources during test. No exceptions are expected neither on server nor on client side.
 * <p/>
 * https://bugzilla.redhat.com/show_bug.cgi?id=1193926 - RejectedExecutionException when closing ModelControllerClient client
 * https://bugzilla.redhat.com/show_bug.cgi?id=1199319 - ModelControllerClientOperationHandler doesn't manage the
 * clientRequestExecutor properly leads to excessive threads leading to hitting process limits/native memory issues
 *
 * @author Petr Kremensky pkremens@redhat.com on 29/04/15.
 */
public class MultipleClientsTestCase extends CLITestBase {

    @Test
    public void multipleClientsTest() throws Exception {
        // creating more requests using multiple clients is currently blocked by BZ1199319
        String result = CLITestExecutor.cliTest(4, 1000, CLISingleRequestWorker.class, JBOSS_HOME,
                this.getClass().getSimpleName());
        Assert.assertTrue("Found some exceptions on client side!\n" + result, result.isEmpty());
    }
}
