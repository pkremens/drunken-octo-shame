package org.jboss.qe.kremilek.data;

import org.jboss.qe.kremilek.Model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Singleton
public class PersonListProducer {
    private List<Person> people;

    @Named
    @Produces
    public List<Person> getPeople() {
        return people;
    }

    @PostConstruct
    public void constructInitialPeople() {
        people = Arrays.asList(
                new Person(1L, "pkremens"),
                new Person(2L, "syzerman")
        );
    }
}
