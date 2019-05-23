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
public class PeopleDB {
    @Inject
    private Logger log;

    private List<Person> people = new ArrayList<>();

    public void createPerson(Person person) {
        log.info("Creating a new persion: " + person.toString());
        people.add(person);
    }

    public List<Person> getPeople() {
        return people;
    }

    @PostConstruct
    private void initPeople() {
        createPerson(new Person(1L, "pkremens"));
        createPerson(new Person(2L, "syzerman"));
    }
}