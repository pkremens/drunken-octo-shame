package org.jboss.qe.management.concurent.keywords;

import org.jboss.qe.management.concurent.utils.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * http://java.dzone.com/articles/java-volatile-keyword-0
 * <p/>
 * Explanation
 * So what happens? Each thread has its own stack, and so its own copy of variables it can access. When the thread is
 * created, it copies the value of all accessible variables in its own memory. The volatile keyword is used to say to
 * the jvm "Warning, this variable may be modified in an other Thread". Without this keyword the JVM is free to make some
 * optimizations, like never refreshing those local copies in some threads. The volatile force the thread to update the original
 * variable for each variable. The volatile keyword could be used on every kind of variable, either primitive or objects!
 * Maybe the subject of another article, more detailed...
 * <p/>
 * Never used volatile and never met this problem...
 * Like all threads issues, it happens under specials circumstances. Really special for this one...
 * My example has big chances to show mainly because the ChangeListener thread is busy, thanks to the loop, and the JVM
 * consider that this thread has no time for updating the local variables. Executing some synchronized methods or adding
 * an other variable which is volatile (or even executing some simple lines of code) could modify the JVM behavior and
 * "correct" this problem...
 * <p/>
 * Should I do a big refactor to identify all variables who needs volatile?
 * Be pragmatic! If you think your project needs it, do it. I think that the essential is to be aware of that, to know
 * what is the goal of each keyword of the java language in order to take the good decisions.
 */
public class VolatileTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(VolatileTest.class.getSimpleName());

    private static volatile int MY_INT = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while (local_value < 5) {
                if (local_value != MY_INT) {
                    LOGGER.info(String.format("Got Change for MY_INT : %d", MY_INT));
                    local_value = MY_INT;
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < 5) {
                LOGGER.log(Level.INFO, "Incrementing MY_INT to {0}", local_value + 1);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
