package org.jboss.qe.management.profiling;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/05/2015
 */
public class ProfilingTestCase {
    File file = new File("/home/pkremens/devel/jbossqe-eap-testsuite-eap6/pom.xml");

    @Test
    public void testSb() throws IOException {
        ReadTest readTest = new SBReadTest();
        test(readTest);
    }

    // List cannot be used to search multiple lines this way!
    @Test
    public void testList() throws IOException {
        ReadTest readTest = new ListReadTest();
        test(readTest);
    }

    private void test(ReadTest readTest) throws IOException {
        readTest.readFile(file);
        readTest.searchString("</project>");
        readTest.print();
    }
}
