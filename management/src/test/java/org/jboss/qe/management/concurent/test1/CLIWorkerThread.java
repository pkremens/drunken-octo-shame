package org.jboss.qe.management.concurent.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/27/15.
 */
public class CLIWorkerThread implements Runnable {
    private final String command = "/home/pkremens/workspace/jboss-eap-6.4/bin/jboss-cli.sh";
    private int identifier;
    private Long timeout;


    public CLIWorkerThread(int identifier, Long timeout) {
        this.identifier = identifier;
        this.timeout = timeout;
    }

    public CLIWorkerThread(int identifier) {
        this(identifier, 10000L);
    }


    @Override
    public void run() {
        final long startTime = System.currentTimeMillis();
        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command, "-c", ":read-attribute(name=server-state)");
        StringBuilder sb = new StringBuilder();
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
//                System.out.println(line);
                sb.append(line).append("\n");
                if (System.currentTimeMillis() - startTime > timeout) {
                    process.destroy();
                    System.out.println("Destroying " + identifier);
                    break;
                }
            }
            br.close();
            if (!sb.toString().contains("\"outcome\" => \"success\"")) {
                System.out.printf(sb.toString());
            }
            System.out.println("Finished: " + identifier + " in " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


