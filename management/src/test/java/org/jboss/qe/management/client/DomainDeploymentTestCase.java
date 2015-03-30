package org.jboss.qe.management.client;

import org.jboss.as.controller.client.helpers.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Petr Kremensky pkremens@redhat.com on 3/30/15.
 *         <p/>
 *         mvn test -Dtest=DomainDeploymentTestCase -Peap630 -s /home/pkremens/devel/jbossqe-eap-testsuite-eap6/tools/maven/conf/settings.xml
 */
public class DomainDeploymentTestCase {
    private static final int MNGMT_PORT = 9999;
    private DomainClient client;

    @Before
    public void getClient() throws UnknownHostException {
        client = DomainClient.Factory.create(InetAddress.getByName("localhost"), MNGMT_PORT);
    }

    @After
    public void closeClient() throws IOException {
        client.close();
    }

    @Test
    public void dummy1() {
        for (String host : client.getHostControllerNames()) {
            System.out.println(host);
        }
    }

    @Test
    public void deployTest() throws Exception {
        deploy();
    }

    public void deploy() throws IOException {
//        DomainClient client = DomainClient.Factory.create(InetAddress.getByName("localhost"), MNGMT_PORT);
        DomainDeploymentManager manager = client.getDeploymentManager();
        DeploymentPlanBuilder builder = manager.newDeploymentPlan();
        DeploymentPlan plan;
        File archive = new File("/home/pkremens/jboss-helloworld.war");
        String serverGroup = "main-server-group";
        DeploymentPlanResult result;
        try {
            plan = builder.add(archive).deploy(archive.getName()).toServerGroup(serverGroup).build();
        } catch (DuplicateDeploymentNameException ex) {
            plan = builder.replace(archive).deploy(archive.getName()).toServerGroup(serverGroup).build();
        }
        if (plan != null) {
            try {
                result = manager.execute(plan).get(30, java.util.concurrent.TimeUnit.MINUTES);
                Assert.assertTrue(result.isValid());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                client.close();
            }
        } else {
            Assert.fail("Failed to create a plan");
        }
    }
}
