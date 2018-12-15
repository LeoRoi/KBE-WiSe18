package de.htw.ai.kbe.services;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.core.Application;

import static org.junit.Assert.*;

public class AuthWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
            }
        });
    }
}