package org.jboss.qe.kremilek.data;

import org.jboss.qe.kremilek.Model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@ApplicationScoped
public class PersonManager {
    @Inject
    private Logger log;

    private List<Person> personList = new ArrayList<>();

    public void createPerson(Person person) {
        log.info("Creating a new person: " + person.toString());
        personList.add(person);
    }

    public void deletePerson(Person person) {
        log.info("People: " + personList.toString());
        log.info("Contains? " + personList.contains(person));
        log.info("Deleting a person: " + person.toString() + " - " + personList.remove(person));
    }

    public List<Person> getPersonList() {
        return personList;
    }

    @PostConstruct
    public void initPeople() {
        log.info("Initializing a new personList");
        createPerson(new Person(1L, "pkremens"));
        createPerson(new Person(2L, "syzerman"));
    }
}