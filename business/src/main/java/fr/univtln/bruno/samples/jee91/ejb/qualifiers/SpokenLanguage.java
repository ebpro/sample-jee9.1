package fr.univtln.bruno.samples.jee91.ejb.qualifiers;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface SpokenLanguage {
    Language value() default Language.FRANCAIS;

    enum Language {FRANCAIS, ENGLISH}
}
