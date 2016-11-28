package org.jboss.qe.kremilek.rs.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("kremilek-rs-client" + System.lineSeparator());
        runRequest("http://localhost:8080/kremilek-rs-server/rest/json", MediaType.APPLICATION_JSON_TYPE);
    }


    /**
     * The purpose of this method is to run the external REST request.
     *
     * @param url       The url of the RESTful service
     * @param mediaType The mediatype of the RESTful service
     */
    private static String runRequest(String url, MediaType mediaType) {
        String result = null;

        System.out.println("===============================================");
        System.out.println("URL: " + url);
        System.out.println("MediaType: " + mediaType.toString());


        // Using the RESTEasy libraries, initiate a client request
        ResteasyClient client = new ResteasyClientBuilder().build();

        // Set url as target
        ResteasyWebTarget target = client.target(url);

        // Be sure to set the mediatype of the request
        target.request(mediaType);

        // Request has been made, now let's get the response
        Response response = target.request().get();
        result = response.readEntity(String.class);
        response.close();

        // Check the HTTP status of the request
        // HTTP 200 indicates the request is OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed request with HTTP status: " + response.getStatus());
        }

        // We have a good response, let's now read it
        System.out.println("\n*** Response from Server ***\n");
        System.out.println(result);
        System.out.println("\n===============================================");

        return result;
    }
}
