package org.jboss.qa;

import org.jboss.qa.server.Server;
import org.jboss.qa.utils.CLITestsuiteException;
import org.jboss.qa.utils.LoggerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com on 30/04/2015
 */
public abstract class CLITestBase {
    public static final File JBOSS_HOME = new File(System.getProperty("jboss.dist"));
    private static final int START_TIMEOUT = 30;
    private static final int STOP_TIMEOUT = 10; // use this once it's required to shutdown the server asynchronously
    private static final Logger log = LoggerFactory.getLogger(CLITestBase.class.getSimpleName());
    private static final String STARTED_IN = "WFLYSRV0025";

    private Server server;
    private boolean started;

    /**
     * Kill any running instances of EAP server and start a fresh one. Wait till the server is fully booted.
     */
    @Before
    public void startEap() {
        processCleanup();
        server = new Server(JBOSS_HOME);
        server.start();
        started = waitForStart(server);
        if (!started) {
            throw new CLITestsuiteException("Failed to start the server.");
        }
    }

    /**
     * Stop testing EAP server instance, check server logs for exceptions and errors, kill any unexpected EAP processes.
     */
    @After
    public void stopEap() {
        final String[] forbidden = new String[]{"exception", "warm", "fatal", "error"};

        if (started) {
            server.shutdown();
        }
        // check server logs
        String serverLog = server.getOutput();
        processCleanup();
        for (String error : forbidden) {
            Assert.assertFalse("Server log contains some error messages (exception, error, etc.).\n" + serverLog,
                    serverLog.toLowerCase().contains(error));
        }
    }

    boolean waitForStart(Server server) {
        boolean started = false;
        for (int i = 0; i < START_TIMEOUT; i++) {
            sleepForWhile(1);
            started = server.getOutput().contains(STARTED_IN);
            if (started) {
                log.info("Server started successfully.");
                break;
            }
        }
        return started;
    }

    private static void processCleanup() {
        log.info("Process cleanup start.");
        final String identifier = "jboss-modules.jar";
        for (Integer pid : getJavaPids(identifier)) {
            log.warning("Killing process with pid=" + pid);
            killProcess(pid);
        }
    }

    private static Set<Integer> getJavaPids(String processName) {
        Set<Integer> pids = new HashSet<Integer>();
        String command = "ps -ef | grep java | grep " + processName + " | grep -v \" grep \"";
        String result = executeCommand("/bin/sh", "-c", command);
        String[] lines = result.split("\n");
        for (String process : lines) {
            if (process.length() > 1) {
                String[] splitLine = process.split("\\s+");
                pids.add(new Integer(splitLine[1]));
            }
        }
        return pids;
    }


    private static String killProcess(int pid) {
        return executeCommand("kill", "-9", String.valueOf(pid));
    }

    private static String executeCommand(String... executionCommand) {
        ProcessBuilder builder = new ProcessBuilder(executionCommand);
        builder.redirectErrorStream(true);
        StringBuilder command = new StringBuilder();
        for (String part : executionCommand) {
            command.append(part).append(" ");
        }
        String result;
        log.info("Executing: " + command.toString());
        try {
            result = executionHandler(builder);
        } catch (Exception ex) {
            throw new CLITestsuiteException("Unable to start process with command: " + command.toString());
        }
        return result;
    }

    private static String executionHandler(ProcessBuilder builder) throws IOException, InterruptedException {
        Process process = builder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder(1024);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        process.waitFor();
        return sb.toString();
    }

    private static void sleepForWhile(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
