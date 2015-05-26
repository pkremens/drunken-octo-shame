package org.jboss.qe.management.concurent.test1;

import org.jboss.qe.management.concurent.measure.Measure;
import org.jboss.qe.management.concurent.utils.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * to monitor the number of opened threads manually ps huH p <PID> | wc -l
 *
 * @author Petr Kremensky pkremens@redhat.com on 4/27/15.
 */
public class CLIThreadPool {
    private static final Logger log = LoggerFactory.getLogger(CLIThreadPool.class.getSimpleName());
    private static Measure measure;
    private static int exceptions = 0;

    public static void main(String[] args) throws Exception {
        measure = new Measure();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 80; i++) {
            Runnable worker = new CLIWorkerAsyncThread(i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            System.out.println(measure.measure());
            Thread.sleep(20000L); // we have to wait till the end of sleep time once all client threads finish
        }
        System.out.println("Finished all threads");
        System.out.println(measure.measure());

        if (exceptions > 0) {
            log.warning("Client operations were not exceptions free. See logs");
        }
    }

    public static synchronized void increaseExceptions() {
        exceptions++;
    }
}