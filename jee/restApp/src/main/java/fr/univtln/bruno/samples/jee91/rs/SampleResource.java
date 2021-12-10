package fr.univtln.bruno.samples.jee91.rs;


import fr.univtln.bruno.samples.jee91.dao.MainDAO;
import fr.univtln.bruno.samples.jee91.dao.Person;
import fr.univtln.bruno.samples.jee91.dao.PersonDAO;
import fr.univtln.bruno.samples.jee91.ejb.Hello;
import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestScoped
@Path("sample")
@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public class SampleResource {

    @Inject
    @SpokenLanguage(SpokenLanguage.Language.ENGLISH)
    Hello hello;

    @Inject
    MainDAO mainDAO;

    @Inject
    PersonDAO personDAO;

    private final String message = "Hello";

    @GET
    public Response message() {
        return Response.ok(message).build();
    }

    @GET
    @Path("hello")
    public String hello() {
        return hello.sayHello();
    }

    @GET
    @Path("main")
    public Map<String, Object> mainGetMetadata() {
        return mainDAO.getMetadata();
    }

    @GET
    @Path("persons")
    public List<Person> personsFindAll() {
        return personDAO.findAll();
    }

    @GET
    @Path("persons/{uuid}")
    public Person personsFind(@PathParam("uuid") UUID uuid) {
        return personDAO.findByUUID(uuid);
    }

    @Transactional
    @POST
    @Path("persons")
    @Consumes(MediaType.APPLICATION_JSON)
    public UUID personsPersist(Person person) {
        return personDAO.persist(person);
    }

}
