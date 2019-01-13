package de.htw.ai.kbe.config;

import de.htw.ai.kbe.di.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

//locale definition instead of web.xml
@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        register(new DependencyBinder());
        packages("de.htw.ai.kbe");
    }
}
