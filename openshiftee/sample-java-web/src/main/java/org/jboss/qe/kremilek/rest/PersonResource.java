package org.jboss.qe.kremilek.rest;

import org.jboss.qe.kremilek.data.PersonManager;
import org.jboss.qe.kremilek.model.Person;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Path("/persons")
@RequestScoped
public class PersonResource {

    @Inject
    PersonManager personManager;

    @GET // http://localhost:8080/sample-java-web/rest/persons
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() {
        return personManager.getPersons();
    }

    @GET // http://localhost:8080/sample-java-web/rest/persons/1
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonById(@PathParam("id") long id) {
        return personManager.getPersonById(id);
    }
}
