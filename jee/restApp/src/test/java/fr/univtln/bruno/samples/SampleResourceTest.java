package fr.univtln.bruno.samples;

import fr.univtln.bruno.samples.jee91.ejb.Hello;
import fr.univtln.bruno.samples.jee91.ejb.HelloBean1;
import fr.univtln.bruno.samples.jee91.ejb.HelloBean2;
import fr.univtln.bruno.samples.jee91.rs.JAXRSConfiguration;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(ArquillianExtension.class)
@Log
public class SampleResourceTest {

    private Client client;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Hello.class)
                .addClasses(HelloBean1.class, HelloBean2.class)
                .addClasses(SampleResourceTest.class, JAXRSConfiguration.class)
                // Enable CDI
                .addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
    }

    @BeforeEach
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    @AfterEach
    public void teardown() {
        if (this.client != null) {
            this.client.close();
        }
    }

    @Test
    @DisplayName("Check that SampleResource says 'hello'")
    @RunAsClient
    public void shouldSayHello(@ArquillianResource URL base) throws MalformedURLException {
        final WebTarget sampleResourceTarget = client.target(new URL(base, "sample").toExternalForm());
        try (final Response sampleResourceGetResponse = sampleResourceTarget.request()
                .accept(MediaType.APPLICATION_JSON)
                .get()) {
            assertThat(sampleResourceGetResponse.getStatus(), is(equalTo(200)));
            assertThat(sampleResourceGetResponse.readEntity(String.class), equalTo("Hello"));
        }
    }
}
