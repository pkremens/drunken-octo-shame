package org.jboss.qa.checker

import org.jboss.qa.checker.model.Computer
import org.jboss.qa.checker.rest.ComputerService
import org.junit.Assert
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: pkremens
 * Date: 8/5/14
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
class CheckerTest {
    @Test
    void firstTest() {
        Assert.assertTrue true
    }

    private static ComputerService service = new ComputerService();
    private Set<Computer> slaves;
    private final String label = "RHEL6";

    @Test
    void testAllSlaves() {
        slaves = service.getSlaves();
        printInfo("All slaves", slaves);
        Assert.assertFalse("Cannot find any slave connected to Jenkins.", slaves.isEmpty());

    }

    @Test
    void testOnlineSlaves() {
        slaves = service.getOnlineSlaves();
        printInfo("Online slaves", slaves);
        Assert.assertFalse("Cannot find any online slave connected to Jenkins.", slaves.isEmpty());
    }

    @Test
    void isOnlineAllTest() {
        int all = service.getSlaves().size();
        int online = service.getOnlineSlaves().size();
        Assert.assertFalse("Number of online slaves cannot be greater than number of all connected slaves",
                online > all);
    }


    @Test
    void testLabeledSlaves() {
        slaves = service.getSlavesByLabel(label);
        printInfo("All " + label + " slaves", slaves);
        Assert.assertFalse("Cannot find any slave connected to Jenkins with " + label + " label.", slaves.isEmpty());
    }

    @Test
    void testOnlineLabeledSlaves() {
        slaves = service.getOnlineSlavesByLabel(label);
        printInfo("Online " + label + " slaves", slaves);
        Assert.assertFalse("Cannot find any online slave connected to Jenkins with " + label + " label.", slaves.isEmpty());
    }

    @Test
    void isOnlineLabeledTest() {
        int all = service.getSlavesByLabel(label).size();
        int online = service.getOnlineSlavesByLabel(label).size();
        Assert.assertFalse("Number of online slaves with " + label + " label cannot be greater than number of all connected slaves",
                online > all);
    }


    private static void printInfo(String label, Set<Computer> computers) {
        System.out.println("#####     " + label + "     #####");
        System.out.println("Slave count: " + computers.size());
        System.out.println(computers);
        System.out.println();
    }

}
