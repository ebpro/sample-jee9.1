package fr.univtln.bruno.samples.jee91.ejb;

import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import jakarta.ejb.Stateless;

@Stateless
@SpokenLanguage(SpokenLanguage.Language.ENGLISH)
public class HelloBean2 implements Hello {
    @Override
    public String sayHello() {
        return "Hi";
    }
}
