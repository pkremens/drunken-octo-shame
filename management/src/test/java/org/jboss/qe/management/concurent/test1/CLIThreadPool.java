package org.jboss.qe.management.concurent.test1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * to monitor the number of opened threads manually ps huH p <PID> | wc -l
 *
 * @author Petr Kremensky pkremens@redhat.com on 4/27/15.
 */
public class CLIThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 130; i++) {
            Runnable worker = new CLIWorkerAsyncThread(i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
