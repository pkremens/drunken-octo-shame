package org.jboss.qe.kremilek.controller;

import org.jboss.qe.kremilek.Model.Person;
import org.jboss.qe.kremilek.data.PeopleDB;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class PersonController {

    @Inject
    private Logger log;

    @Inject
    private PeopleDB peopleDB;

    @Inject
    private Event<Person> memberEventSrc;

//    @Produces
//    @Named
//    private Person newPerson;
//
//    @PostConstruct
//    public void initNewMember() {
//        newPerson = new Person();
//    }

    @Named
    public void deletePerson(Person person) {
        peopleDB.deletePerson(person);
        memberEventSrc.fire(person);
    }

//    public void register() {
//        people.
//    }
}
