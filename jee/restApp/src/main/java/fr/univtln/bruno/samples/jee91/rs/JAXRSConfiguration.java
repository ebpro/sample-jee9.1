package fr.univtln.bruno.samples.jee91.rs;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/")
public class JAXRSConfiguration extends Application {
    @Override
    public Map<String, Object> getProperties() {
        var properties = new HashMap<String, Object>();
        properties.put("jersey.config.jsonFeature", "JacksonFeature");
        return properties;
    }
}