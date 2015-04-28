package org.jboss.qe.management.concurent.test1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/27/15.
 */
public class CLIThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new CLIWorkerAsyncThread(i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
