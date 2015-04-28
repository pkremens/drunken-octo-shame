package org.jboss.qe.management.concurent.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/27/15.
 */
public class WorkerDemo {
    public static void main(String[] args) {
        Process process;
        ProcessBuilder pb;
        String command = "/home/pkremens/workspace/jboss-eap-6.4/bin/jboss-cli.sh";
        pb = new ProcessBuilder("/bin/sh", command, "-c", ":read-resource");
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start process with command: " + command);
        }
        InputStreamReader isr = new InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
