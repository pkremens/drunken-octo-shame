package org.jboss.qa.worker;

import org.jboss.qa.utils.CLITestsuiteException;
import org.jboss.qa.utils.LoggerFactory;

import java.io.*;
import java.util.logging.Logger;

/**
 * CLI client runner which connects to running EAP instance via jboss-cli.sh script located in $JBOSS_HOME/bin directory,
 * perform :read-attribute(name=server-state) operation and disconnects. Request timeout is 10s by default.
 *
 * @author Petr Kremensky pkremens@redhat.com on 4/28/15.
 */
public class CLISingleRequestWorker implements Runnable {
    private final String command;
    private final int identifier;
    private final Long timeout;
    private static final Logger log = LoggerFactory.getLogger(CLISingleRequestWorker.class.getSimpleName());


    /**
     * Create a new single request jboss-cli client.
     *
     * @param identifier Request indentifier.
     * @param jbossHome  Path to EAP (JBOSS_HOME property).
     * @param timeout    Timeout used by client. Destroy the request if we are unable to receive any response from
     *                   server in given time.
     */
    public CLISingleRequestWorker(int identifier, File jbossHome, Long timeout) {
        this.identifier = identifier;
        this.command = jbossHome.getAbsolutePath() + "/bin/jboss-cli.sh";
        this.timeout = timeout;
    }

    /**
     * Create a new single request jboss-cli client with default timeout 10s.
     *
     * @param identifier Request indentifier.
     * @param jbossHome  Path to EAP (JBOSS_HOME property).
     */
    public CLISingleRequestWorker(int identifier, File jbossHome) {
        this(identifier, jbossHome, 10000L);
    }


    @Override
    public void run() {
        String id = String.format("request %d by: %s", identifier, Thread.currentThread().getName());
        final long startTime = System.currentTimeMillis();
        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command, "-c", ":read-attribute(name=server-state)");
        pb.redirectErrorStream(true);
        final Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new CLITestsuiteException("Unable to start process with command: " + command);
        }
        AsyncReader reader = new AsyncReader(process.getInputStream());
        reader.start();
        try {
            reader.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (reader.isAlive()) {
            log.warning("Destroying " + id);
            process.destroy();
            reader.interrupt();
        }
        if (!verifyOutcome(reader.getOutput())) {
            CLITestExecutor.addException(id + "\n" + reader.getOutput());
            log.warning(reader.getOutput());
        }
        log.info(String.format("Finished: %s in %dms", id, System.currentTimeMillis() - startTime));
    }

    /*
     * This is expected output:
     * {
     * "outcome" => "success",
     * "result" => "running"
     * }
     * <p/>
     * nothing more, nothing less.
     */
    private boolean verifyOutcome(String output) {
        String[] outLines = output.split("\n");
        return outLines.length == 4 &&
                outLines[0].equals("{") &&
                outLines[1].equals("    \"outcome\" => \"success\",") &&
                outLines[2].equals("    \"result\" => \"running\"") &&
                outLines[3].equals("}");
    }

    private class AsyncReader extends Thread {
        private final StringBuilder sb = new StringBuilder();
        private final BufferedReader br;

        private AsyncReader(InputStream inputStream) {
            this.br = new BufferedReader(new InputStreamReader(inputStream));
        }

        @Override
        public void run() {
            String line;
            try {
                while (!br.ready()) {
                    // br.readLine() could stuck without this
                    Thread.sleep(20L);
                }
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                // request timed-out
            } catch (InterruptedException ie) {
                try {
                    log.info("Request timed out, close the stream.");
                    br.close();
                } catch (IOException e) {
                    // swallow this one
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getOutput() {
            return sb.toString();
        }
    }
}
