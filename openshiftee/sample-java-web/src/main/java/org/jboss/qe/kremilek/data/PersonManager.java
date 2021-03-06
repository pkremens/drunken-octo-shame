package org.jboss.qe.kremilek.data;

import org.jboss.qe.kremilek.model.Person;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Stateless
public class PersonManager {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public Person addPerson(String name) {
        Person personToAdd = new Person(name);
        log.info("Creating a new person: " + personToAdd.toString());
        em.persist(personToAdd);
        return personToAdd;
    }

    public void deletePerson(Long id) {
        Person personToDelete = getPersonById(id);
        log.info("Deleting a person: " + personToDelete.toString());
        em.remove(personToDelete);
    }

    public Person getPersonById(Long id) {
        return em.find(Person.class, id);
    }

    @PostConstruct
    public void initPeople() {
        log.info("Initializing a new personList");
        addPerson("Petr Kremensky");
        addPerson("Steve Yzerman");
        addPerson("Matt Hardy");
        addPerson("Vincenzo Nibali");
        addPerson("Michael Schumacher");
    }

    /**
     * Get all customers
     *
     * @return all customers in DB
     */
    public List<Person> getPersons() {
        log.info("Get all Persons");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> person = criteria.from(Person.class);
        criteria.select(person);
//        criteria.orderBy(cb.asc(customer.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}