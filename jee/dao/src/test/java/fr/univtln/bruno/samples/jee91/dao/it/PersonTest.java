package fr.univtln.bruno.samples.jee91.dao.it;

import fr.univtln.bruno.samples.jee91.dao.PersonDAO;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ArquillianExtension.class)
@Slf4j
class PersonTest {

    @Inject
    PersonDAO personDAO;

    @Inject
    UserTransaction utx;
    @PersistenceContext(unitName = "myappPU")
    private EntityManager em;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        /*File[] libs = Maven.configureResolver().loadPomFromFile("pom.xml")
                .importRuntimeAndTestDependencies().resolve()
                .withTransitivity().asFile();*/

        return ShrinkWrap.create(WebArchive.class, "arquilian-dao-test.war")
                .addPackage(PersonDAO.class.getPackage())
                .addPackage(fr.univtln.bruno.samples.jee91.dao.entitymanagersproducers.H2Database.class.getPackage())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-log4j12:1.7.32").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.fasterxml.jackson.core:jackson-databind:2.13.0").withTransitivity().asFile())
                .addAsWebInfResource("web.xml")
                .addAsResource("insert.sql")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
//                .addAsLibraries(libs);


    }

    @Test
    @DisplayName("Testing Person findall")
    public void shouldInjectHelloBean1() {
        log.info(personDAO.findAll().toString());
    }

}