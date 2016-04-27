package org.jboss.qe.kremilek.modules.client;

import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Petr Kremensky pkremens@redhat.com on 25/04/2016
 */
public class MultiHierarchicalClient implements Runnable {
    private static Random r = new Random();
    private static final String ROOT = "multi-hierarchical-web-ui-1.0-SNAPSHOT";
    private int requestNo;

    public MultiHierarchicalClient(int requestNo) {
        this.requestNo = requestNo;
    }

    public void run() {
        String content = null;
        URLConnection connection = null;
        try {
            connection = new URL("http://localhost:8080/" + ROOT + "/resources/" + ((char) (r.nextInt(26) + 'a'))).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
//            System.out.println(Thread.currentThread().getName() + " finished: " + content + " " + requestNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}