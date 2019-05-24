package org.jboss.qe.kremilek.controller;

import org.jboss.qe.kremilek.Model.Person;
import org.jboss.qe.kremilek.data.PersonManager;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class PersonController {

    @Inject
    private Logger log;

    @Inject
    private PersonManager personManager;

//    private List<Person> persons;

//    @Named("people")
    public List<Person> getPersons() {
        log.info("People from person manager: " + personManager.getPersonList().toString());
        return personManager.getPersonList();
    }

    public void deletePerson(Person person) {
        personManager.deletePerson(person);
    }

    public void resetPersons() {
        personManager.initPeople();
    }
}
