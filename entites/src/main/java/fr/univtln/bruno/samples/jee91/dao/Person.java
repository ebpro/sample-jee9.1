package fr.univtln.bruno.samples.jee91.dao;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.findAll",
                query = "select p from Person p"),
        @NamedQuery(
                name = "Person.findAllOrderedByName",
                query = "SELECT p FROM Person p ORDER BY p.name")
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;
}
