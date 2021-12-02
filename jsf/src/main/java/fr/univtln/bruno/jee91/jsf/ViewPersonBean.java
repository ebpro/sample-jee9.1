package fr.univtln.bruno.jee91.jsf;

import fr.univtln.bruno.samples.jee91.dao.Person;
import fr.univtln.bruno.samples.jee91.dao.PersonDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class ViewPersonBean {

    @Inject
    PersonDAO personDAO;

    private String message = "Hello";

    public String getMessage() {
        return this.message;
    }

    public List<Person> getPersons() {
        return personDAO.findAll();
    }

}
