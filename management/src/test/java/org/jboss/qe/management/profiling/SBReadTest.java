package org.jboss.qe.management.profiling;

import java.io.*;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/05/2015
 */
public class SBReadTest implements ReadTest {
    private StringBuilder sb = new StringBuilder();

    @Override
    public void readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }

    @Override
    public boolean searchString(String text) {
        return sb.toString().contains(text);
    }

    @Override
    public void print() {
        System.out.println(sb.toString());
    }

}
