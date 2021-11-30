package fr.univtln.bruno.samples.jee91.dao;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Table(name = "PERSON")
@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Person.findAll",
                query = "select p from Person p"),
        @NamedQuery(
                name = "Person.findAllOrderedByName",
                query = "SELECT p FROM Person p ORDER BY p.name"),
        @NamedQuery(
                name = "Person.findByUUID",
                query = "SELECT p FROM Person p WHERE p.uuid=:uuid")
})
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonbTransient
    long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "UUID", updatable = false, nullable = false, unique = true)
    UUID uuid = UUID.randomUUID();
}
