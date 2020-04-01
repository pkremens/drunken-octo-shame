package org.jboss.qe.kremilek.openshiftee.client;

import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Fabric8ClientDemo {
    public static void main(String[] args) {
        try (OpenShiftClient client = new DefaultOpenShiftClient()) {
            System.out.println("Client opened is: " + client);
            // either use:
//            ((DefaultOpenShiftClient) client).inNamespace("pkremens-namespace")
            // or manually switch to desired project using oc binary cmd
                    client.pods().list().getItems().stream().forEach(
                    p -> System.out.println("pod: " + p.getMetadata().getName() + " - " + p.getStatus().getPhase()));
        }
    }
}
