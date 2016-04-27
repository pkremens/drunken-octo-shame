package org.jboss.qe.kremilek.modules.client

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author Petr Kremensky pkremens@redhat.com on 25/04/2016
 */
ExecutorService executor = Executors.newFixedThreadPool(5);
def Random r = new Random();
def String ROOT = "multi-hierarchical-web-ui-1.0-SNAPSHOT";

for (int i = 0; i < 500; i++) {
    println ('asd')
    Runnable run = new Runnable() {
        @Override
        public void run() {
            println new URL("http://localhost:8080/" + ROOT + "/resources/" + ((char) (r.nextInt(26) + 'a'))).text
        }

    };
    executor.submit(run);
}

