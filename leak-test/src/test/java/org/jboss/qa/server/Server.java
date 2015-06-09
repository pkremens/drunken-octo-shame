package org.jboss.qa.server;

import org.jboss.qa.utils.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Class representing the EAP server instance. Server logs are stored in property, however they are also printed to
 * stdout (pair with client logs, see immediately the reason of stuck - of any).
 *
 * @author Petr Kremensky pkremens@redhat.com on 29/04/15.
 */
public class Server extends Thread {
    private final StringBuilder consoleLog = new StringBuilder();
    private final File jbossHome;
    private static final Logger log = LoggerFactory.getLogger(Server.class.getSimpleName());

    /**
     * Create a new EAP server instance. Use start() method to start server in standalone mode. Server lifecycle has to
     * be maintained manually (start() method just start the server, doesn't wait till it's fully booted).
     *
     * @param jbossHome Path to EAP server ($JBOSS_HOME property)
     */
    public Server(File jbossHome) {
        this.jbossHome = jbossHome;
    }

    @Override
    public void run() {
        String command = jbossHome.getAbsolutePath() + "/bin/standalone.sh";

        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", command);
        pb.redirectErrorStream(true);
        final Process process;

        try {
            log.info("Starting standalone EAP server from location: " + jbossHome.getAbsolutePath());
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

    /**
     * Shutdown the standalone EAP server via jboss-cli.sh script.
     *
     * @return Shutdown command output,
     */
    public String shutdown() {
        String command = jbossHome.getAbsolutePath() + "/bin/jboss-cli.sh";
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

    /**
     * Get the console logs of EAP server.
     *
     * @return Console logs of EAP standalone server process.
     */
    public String getOutput() {
        return consoleLog.toString();
    }

    /**
     * Clear the buffered server logs.
     *
     * @return Server logs accumulated prior this clean up.
     */
    public String clearOutput() {
        String serverLog = consoleLog.toString();
        consoleLog.setLength(0);
        return serverLog;
    }
}
