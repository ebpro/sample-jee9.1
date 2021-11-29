package fr.univtln.bruno.samples.jee91.dao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DAO2 {
    private EntityManager entityManager;

    @Inject
    public DAO2(@H2Database EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return entityManager.createNamedQuery("Person.findAll").getResultList();
    }

}
