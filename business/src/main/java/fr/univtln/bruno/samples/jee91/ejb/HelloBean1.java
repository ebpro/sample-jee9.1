package fr.univtln.bruno.samples.jee91.ejb;

import fr.univtln.bruno.samples.jee91.ejb.qualifiers.SpokenLanguage;
import jakarta.ejb.Stateless;

@Stateless
public class HelloBean1 implements Hello {
    @Override
    @SpokenLanguage(SpokenLanguage.Language.FRANCAIS)
    public String sayHello() {
        return "Salut";
    }
}
