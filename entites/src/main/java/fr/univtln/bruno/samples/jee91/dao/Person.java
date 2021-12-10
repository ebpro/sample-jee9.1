package fr.univtln.bruno.samples.jee91.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "PERSON")
@Entity
@Getter
@Setter
@NamedQuery(name = "Person.findAll",
        query = "select p from Person p")
@NamedQuery(
        name = "Person.findAllOrderedByName",
        query = "SELECT p FROM Person p ORDER BY p.name")
@NamedQuery(
        name = "Person.findByUUID",
        query = "SELECT p FROM Person p WHERE p.uuid=:uuid")
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")

    @JsonIgnore
    long id;

    @ToString.Include
    @Column(name = "NAME")
    @NotBlank
    @Pattern(regexp = "[A-Z][a-z]+", message = "Le nom doit commencer par une majuscule suivie de minuscules")
    String name;

    @ToString.Include
    @Column(name = "UUID", updatable = false, nullable = false, unique = true)
    UUID uuid = UUID.randomUUID();

    @Builder
    public Person(String name) {
        this.name = name;
    }
}
