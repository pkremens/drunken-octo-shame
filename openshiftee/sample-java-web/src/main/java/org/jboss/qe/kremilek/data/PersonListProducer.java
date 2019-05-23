package org.jboss.qe.kremilek.data;

import org.jboss.qe.kremilek.Model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@RequestScoped
public class PersonListProducer {
    private List<Person> people;

    @Inject
    private Logger log;

    @Inject
    PeopleDB peopleDB;

    @Named
    @Produces
    public List<Person> getPeople() {
        return people;
    }

    @PostConstruct
    public void updatePeopleList() {
        people = peopleDB.getPeople();
    }

    public void onPeopleListChange(@Observes(notifyObserver = Reception.IF_EXISTS) final Person person) {
        log.info("Event caught on " + person.toString());
        updatePeopleList();
        log.info("Updated list:" + people.toString());
    }
}
