package org.jboss.qe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Petr Kremensky pkremens@redhat.com on 23/06/2015
 */
public class StaticBooleanInThreadDemo {
    static AtomicBoolean done = new AtomicBoolean(false);
//    static volatile boolean done = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            int count = 0;

            @Override
            public void run() {
                while (!done.get()) {
//                while (!done) {
                    count++;
                }
                System.out.println("Thread done: " + count);
            }
        }).start();

        Thread.sleep(2000);
//        done = true;
        done.set(true);
        System.out.println("Main done");
    }
}
