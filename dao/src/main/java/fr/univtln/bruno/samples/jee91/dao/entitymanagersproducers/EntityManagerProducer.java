package fr.univtln.bruno.samples.jee91.dao.entitymanagersproducers;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class EntityManagerProducer {

    @Produces
    @PersistenceContext(unitName = "myappPU")
    @H2Database
    private EntityManager entityManager;

}
