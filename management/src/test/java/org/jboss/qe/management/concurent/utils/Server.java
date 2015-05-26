package org.jboss.qe.management.concurent.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class Server extends Thread {
    private StringBuilder consoleLog = new StringBuilder();

    @Override
    public void run() {
        String command = "/home/pkremens/workspace/jboss-eap-6.4/bin/standalone.sh";

        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command);
        pb.redirectErrorStream(true);
        final Process process;

        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start process with command: " + command);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                consoleLog.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String shutdown() {
        String command = "/home/pkremens/workspace/jboss-eap-6.4/bin/jboss-cli.sh";
        StringBuilder output = new StringBuilder();
        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command, "-c", "shutdown");
        pb.redirectErrorStream(true);
        final Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start process with command: " + command);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                output.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();

    }

    public String getOutput() {
        return consoleLog.toString();
    }
}
