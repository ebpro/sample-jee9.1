package fr.univtln.bruno.samples.jee91.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import java.util.Map;

public class DAO1 {

    @PersistenceUnit(unitName="myappPU")
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext(unitName = "myappPU")
    private EntityManager entityManager;

    public Map<String, Object> getMetadata() {
        return entityManagerFactory.getProperties();
    }
}
