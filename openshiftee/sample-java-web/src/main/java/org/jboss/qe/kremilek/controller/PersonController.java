package org.jboss.qe.kremilek.controller;

import org.jboss.qe.kremilek.Model.Person;
import org.jboss.qe.kremilek.data.PersonListProducer;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class PersonController {

    @Produces
    @Named
    private Person newPerson;

    @Inject
    PersonListProducer people;

    @PostConstruct
    public void initNewMember() {
        newPerson = new Person();
    }

//    public void register() {
//        people.
//    }
}
