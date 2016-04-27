package org.jboss.qe.kremilek.modules.client;

import org.jboss.as.cli.scriptsupport.CLI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Petr Kremensky pkremens@redhat.com on 25/04/2016
 */
public class Demo {
    private static CLI cli;
    private static Measure measure;
    private static final String archiveName = "multi-hierarchical-web-ui-1.0-SNAPSHOT.war";
    private static final String archivePath = "/home/pkremens/devel/multi-module-performance/multi-hierarchical/multi-hierarchical-web-ui/target/" + archiveName;

    public static void main(String[] args) throws Exception {
        init();
        for (int i = 0; i < 20; i++) {
            print(i + ". iteration:");
            deploy();
            runRequests(1000000, 10);
            undeploy();
        }
        cli.disconnect();
    }

    private static void print(String text) {
        System.out.println("\n#####     " + text.toUpperCase() + "     #####");
    }

    private static void init() throws Exception {
        print("init");
        cli = CLI.newInstance();
        cli.connect();
        measure = new Measure("localhost", 9990);
        sleepForAwhile(2);
        System.out.println(measure.measure());
    }

    private static void executeCommand(String command) {
        CLI.Result result = cli.cmd(command);
        System.out.println(result.getResponse().asString());
    }

    private static void runRequests(int requests, int poolSize) throws Exception {
        print("run requests");
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < requests; i++) {
            executor.submit(new MultiHierarchicalClient(i));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        print("TOOK: " + (System.currentTimeMillis() - start) / 1000 + " s");
        sleepForAwhile(2);
        System.out.println(measure.measure());
    }

    private static void deploy() throws Exception {
        print("deploy");
        executeCommand("deploy " + archivePath);
        sleepForAwhile(5);
        System.out.println(measure.measure());
    }

    private static void undeploy() throws Exception {
        print("undeploy");
        executeCommand("undeploy " + archiveName);
        sleepForAwhile(2);
        System.out.println(measure.measure());
    }

    private static void sleepForAwhile(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

