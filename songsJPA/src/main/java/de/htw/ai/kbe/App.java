package de.htw.ai.kbe;

import de.htw.ai.kbe.di.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

//locale definition instead of web.xml
@ApplicationPath("/rest")
public class App extends ResourceConfig {
    public App() {
        register(new DependencyBinder());
        packages("de.htw.ai.kbe");
    }
}
