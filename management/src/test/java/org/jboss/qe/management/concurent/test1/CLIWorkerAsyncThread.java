package org.jboss.qe.management.concurent.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/28/15.
 */
public class CLIWorkerAsyncThread implements Runnable {
    private final String command = "/home/pkremens/workspace/jboss-eap-6.4/bin/jboss-cli.sh";
    private int identifier;
    private Long timeout;


    public CLIWorkerAsyncThread(int identifier, Long timeout) {
        this.identifier = identifier;
        this.timeout = timeout;
    }

    public CLIWorkerAsyncThread(int identifier) {
        this(identifier, 10000L);
    }


    @Override
    public void run() {
        final long startTime = System.currentTimeMillis();
        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command, "-c", ":read-attribute(name=server-state)");
        pb.redirectErrorStream(true);
        final Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start process with command: " + command);
        }
        AsyncReader reader = new AsyncReader(process.getInputStream());
        reader.start();
        try {
            reader.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (reader.isAlive()) {
            System.out.println("Destroying " + identifier);
            process.destroy();
            reader.interrupt();
        }
        if (!verifyOutcome(reader.getOutput())) {
            System.err.println(reader.getOutput());
        }
        System.out.println("Finished: " + identifier + " in " + (System.currentTimeMillis() - startTime) + "ms");

    }

    /*
     * This is expected output:
     * {
     *   "outcome" => "success",
     *   "result" => "running"
     * }
     *
     * nothing more, nothing less.
     */
    private boolean verifyOutcome(String output) {
        String[] outLines = output.split("\n");
        boolean result = (outLines.length == 4 &&
                outLines[0].equals("{") &&
                outLines[1].equals("    \"outcome\" => \"success\",") &&
                outLines[2].equals("    \"result\" => \"running\"") &&
                outLines[3].equals("}"));
        return result;
    }

    private class AsyncReader extends Thread {
        private final StringBuilder sb = new StringBuilder();
        private BufferedReader br;

        private AsyncReader(InputStream inputStream) {
            this.br = new BufferedReader(new InputStreamReader(inputStream));
        }

        @Override
        public void run() {
            String line;
            try {
                while (!br.ready()) {
                    // br.readLine() could stuck without this
                }
                ;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getOutput() {
            return sb.toString();
        }
    }
}
