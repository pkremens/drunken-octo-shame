package org.jboss.qa.worker;

import org.jboss.qa.utils.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * CLI test execution handler.
 *
 * @author Petr Kremensky pkremens@redhat.com on 4/28/15.
 */
public class CLITestExecutor {
    private static final Logger log = LoggerFactory.getLogger(CLITestExecutor.class.getSimpleName());
    private static StringBuilder exceptions;

    /**
     * Cli clients main executor.
     *
     * @param clients   The number of clients we want to use in the test.
     * @param requests  The number of requests we want to pass to server.
     * @param cliWorker CLI worker class used for the test (see org.jboss.qa.worker.* for available workers)
     * @param jbossHome Path to EAP server (JBOSS_HOME property)
     * @param testName  Name of the test case (used for better UX reading the results)
     * @return List of exceptions caught on client side.
     */
    public static String cliTest(int clients, int requests, Class cliWorker, File jbossHome, String testName) throws Exception {
        exceptions = new StringBuilder();
        if (clients < 1 || requests < 1) {
            throw new IllegalArgumentException("Number of clients and requests has to be greater than one!");
        }
        // introduction
        log.info("##### Starting " + testName);
        log.info("# - clients = " + clients);
        log.info("# - requests = " + requests);
        log.info("##########################################");

        ExecutorService executor = Executors.newFixedThreadPool(clients);
        for (int i = 0; i < requests; i++) {
            Constructor<Runnable> con = cliWorker.getConstructor(int.class, File.class);
            Runnable worker = con.newInstance(i, jbossHome);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(10000); // we have to wait till the end of sleep time once all client threads finish
        }
        log.info("Finished all threads");
        if (exceptions.length() > 0) {
            log.warning("Client operations were not exceptions free. See logs");
        }
        return exceptions.toString();
    }

    /**
     * We need to store exceptions caught on client side. Store every exception by adding an entry into exceptions list.
     * Exceptions list can controller at the end of each test and be used as a part of JUnit assert message.
     *
     * @param exception The string representation of exception received on client side.
     */
    public static synchronized void addException(String exception) {
        exceptions.append(exception);
    }
}