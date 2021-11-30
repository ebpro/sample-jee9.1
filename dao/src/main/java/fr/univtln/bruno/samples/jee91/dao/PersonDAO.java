package fr.univtln.bruno.samples.jee91.dao;

import fr.univtln.bruno.samples.jee91.dao.entitymanagersproducers.H2Database;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public class PersonDAO {
    private EntityManager entityManager;

    @Inject
    public PersonDAO(@H2Database EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return entityManager.createNamedQuery("Person.findAll").getResultList();
    }

    public UUID persist(Person person) {
        entityManager.persist(person);
        return person.getUuid();
    }

    public Person findByUUID(UUID uuid) {
        return entityManager.createNamedQuery("Person.findByUUID", Person.class).setParameter("uuid",uuid).getSingleResult();
    }

}
