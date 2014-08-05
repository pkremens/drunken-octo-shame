/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.qa.checker;

import org.jboss.qa.checker.model.Computer;
import org.jboss.qa.checker.rest.ComputerService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author <a href="mailto:pkremens@redhat.com">Petr Kremensky</a>
 */
public class CheckerTestCase {
    private static ComputerService service = new ComputerService();
    private Set<Computer> slaves;
    private final String label = "RHEL6";

    @Test
    public void testAllSlaves() {
        slaves = service.getSlaves();
        printInfo("All slaves", slaves);
        Assert.assertFalse("Cannot find any slave connected to Jenkins.", slaves.isEmpty());

    }

    @Test
    public void testOnlineSlaves() {
        slaves = service.getOnlineSlaves();
        printInfo("Online slaves", slaves);
        Assert.assertFalse("Cannot find any online slave connected to Jenkins.", slaves.isEmpty());
    }

    @Test
    public void isOnlineAllTest() {
        int all = service.getSlaves().size();
        int online = service.getOnlineSlaves().size();
        Assert.assertFalse("Number of online slaves cannot be greater than number of all connected slaves",
                online > all);
    }


    @Test
    public void testLabeledSlaves() {
        slaves = service.getSlavesByLabel(label);
        printInfo("All " + label + " slaves", slaves);
        Assert.assertFalse("Cannot find any slave connected to Jenkins with " + label + " label.", slaves.isEmpty());
    }

    @Test
    public void testOnlineLabeledSlaves() {
        slaves = service.getOnlineSlavesByLabel(label);
        printInfo("Online " + label + " slaves", slaves);
        Assert.assertFalse("Cannot find any online slave connected to Jenkins with " + label + " label.", slaves.isEmpty());
    }

    @Test
    public void isOnlineLabeledTest() {
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
