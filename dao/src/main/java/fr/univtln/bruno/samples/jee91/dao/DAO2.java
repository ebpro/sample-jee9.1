package fr.univtln.bruno.samples.jee91.dao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class DAO2 {
    private EntityManager entityManager;

    @Inject
    public DAO2(@H2Database EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
