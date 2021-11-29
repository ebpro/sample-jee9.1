package fr.univtln.bruno.samples.jee91.rs;


import fr.univtln.bruno.samples.jee91.ejb.Hello;
import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import fr.univtln.bruno.samples.jee91.dao.DAO1;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;

@Path("sample")
public class SampleResource {

    @Inject
    @SpokenLanguage(SpokenLanguage.Language.ENGLISH)
    Hello hello;

    @Inject
    DAO1 dao1;

    @Inject
    @ConfigProperty(name = "message")
    private String message;

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
    @Path("dao1")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> dao1() {
        return dao1.getMetadata();
    }

    @GET
    @Path("dao2")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> dao2() {
        return dao1.getMetadata();
    }
}
