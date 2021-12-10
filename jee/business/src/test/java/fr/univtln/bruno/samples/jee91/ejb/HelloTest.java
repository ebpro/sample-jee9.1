package fr.univtln.bruno.samples.jee91.ejb;

import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
@Log
class HelloTest {
    @Inject
    Hello hello1;

    @Inject
    @SpokenLanguage(SpokenLanguage.Language.ENGLISH)
    Hello hello2;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(HelloBean1.class)
                .addClass(HelloBean2.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @DisplayName("Testing Hello in French")
    public void shouldInjectHelloBean1() {
        assertEquals(hello1.sayHello(), "Salut");
    }

    @Test
    @DisplayName("Testing Hello in English")
    public void shouldInjectHelloBean2() {
        assertEquals(hello2.sayHello(), "Hi");
    }

}