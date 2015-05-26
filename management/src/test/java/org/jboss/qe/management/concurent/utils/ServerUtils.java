package org.jboss.qe.management.concurent.utils;

import org.jboss.qe.management.concurent.test1.CLIThreadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class ServerUtils {
    private static final Logger log = LoggerFactory.getLogger(ServerUtils.class.getSimpleName());

    private static final File binDir = new File("/home/pkremens/workspace/jboss-eap-6.4/bin");

    public static void processCleanup() throws Exception {
        log.info("Process cleanup start.");
        final String identifier = "jboss-modules.jar";
        for (Integer pid : getJavaPids(identifier)) {
            log.warning("Killing process with pid=" + pid);
            log.info(killProcess(pid));
        }
    }

    private static String executeCommand(String executionCommand) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(executionCommand);
        builder.redirectErrorStream(true);
        String result;
        try {
            result = executionHandler(builder);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to start process with command: " + executionCommand);
        }
        return result;
    }

    public static String executeCommand(String[] executionCommand) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(executionCommand);
        builder.redirectErrorStream(true);
        StringBuilder command = new StringBuilder();
        for (String part : executionCommand) {
            command.append(part);
        }
        String result;
        log.info("Executing: " + command.toString());
        try {
            result = executionHandler(builder);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to start process with command: " + command.toString());
        }
        return result;
    }

    private static String executionHandler(ProcessBuilder builder) throws Exception {
        Process process = null;
        process = builder.start();
        BufferedReader inStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder(1024);
        String line;
        while ((line = inStream.readLine()) != null) {
            sb.append(line).append("\n");
        }
        process.waitFor();
        return sb.toString();
    }


    public static Set<Integer> getJavaPids(String processName) throws Exception {
        Set<Integer> pids = new HashSet<Integer>();
        String command = "ps -ef | grep java | grep " + processName + " | grep -v \" grep \"";
        String[] commandArray = new String[]{"/bin/sh", "-c", command};
        String result = executeCommand(commandArray);
        String[] lines = result.split("\n");
        for (String process : lines) {
            if (process.length() > 1) {
                String[] splitLine = process.split("\\s+");
                pids.add(new Integer(splitLine[1]));
            }
        }
        return pids;
    }


    public static String killProcess(int pid) throws Exception {
        return executeCommand(new String[]{"kill", "-9", String.valueOf(pid)});
    }

    public static void main(String[] args) throws Exception {
        processCleanup();
        Server server = new Server();
        server.start();

        boolean started = false;
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            started = server.getOutput().contains("JBAS015874");

            if (started) {

                log.info("Server started successfully.");

                break;
            }
        }

        if (!started) {
            log.warning("Failed to start server, clean process and end");
            processCleanup();
            System.exit(1);
        }
        Thread.sleep(1000);
        CLIThreadPool.main(null);

        log.info("Shutdown now");
        server.shutdown();


    }
}
