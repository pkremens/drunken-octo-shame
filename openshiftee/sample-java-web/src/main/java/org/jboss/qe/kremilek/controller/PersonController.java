package org.jboss.qe.kremilek.controller;

import org.jboss.qe.kremilek.data.PersonManager;
import org.jboss.qe.kremilek.model.Person;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
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

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return personManager.getPersons();
    }

    public void addPerson() {
        personManager.addPerson(name);
        name = null;
    }

    public void deletePerson(Long id) {
        personManager.deletePerson(id);
    }

    public void resetPersons() {
        personManager.initPeople();
    }
}
