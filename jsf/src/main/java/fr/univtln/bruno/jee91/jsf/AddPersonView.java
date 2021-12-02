package fr.univtln.bruno.jee91.jsf;

import fr.univtln.bruno.samples.jee91.dao.Person;
import fr.univtln.bruno.samples.jee91.dao.PersonDAO;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;

import java.io.Serializable;

@Named
@ViewScoped
public class AddPersonView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private transient PersonDAO personDAO;

    @Getter
    private Person newPerson = new Person();
    @Getter
    private Person addedPerson = new Person();

    @Transactional
    public void addPerson() {
        FacesMessage facesMessage;
        personDAO.persist(addedPerson = Person.builder().name(newPerson.getName()).build());
        FacesContext.getCurrentInstance().addMessage("growl-id", new FacesMessage(FacesMessage.SEVERITY_INFO, "Person added", addedPerson.getName() + "(" + addedPerson.getUuid() + ")"));
    }
}
