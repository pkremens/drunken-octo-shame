package org.jboss.qe.management.profiling;

import java.io.File;
import java.io.IOException;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/05/2015
 */
public interface ReadTest {

    public void readFile(File file) throws IOException;

    public void print();

    public boolean searchString(String text);
}
