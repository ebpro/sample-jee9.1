package fr.univtln.bruno.samples.jee91.dao;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.findAll",
                query = "select p from Person p"),
        @NamedQuery(
                name = "Person.findAllOrderedByName",
                query = "SELECT p FROM Person p ORDER BY p.name")
})
@XmlRootElement
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;
}
