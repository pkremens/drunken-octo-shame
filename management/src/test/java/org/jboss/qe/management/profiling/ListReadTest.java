package org.jboss.qe.management.profiling;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/05/2015
 */
public class ListReadTest implements ReadTest {
    private List<String> output = new ArrayList<String>();


    @Override
    public void readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = br.readLine()) != null) {
            output.add(line);
        }
    }

    @Override
    public boolean searchString(String text) {
        for (String s : output) {
            if (s.contains(text)) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        for (String line : output) {
            System.out.println(line);
        }
    }

}
