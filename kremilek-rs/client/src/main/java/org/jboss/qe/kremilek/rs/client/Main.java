package org.jboss.qe.kremilek.rs.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("kremilek-rs-client" + System.lineSeparator());
        ResteasyClient client = new ResteasyClientBuilder().build();
    }
}
