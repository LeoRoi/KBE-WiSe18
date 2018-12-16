package de.htw.ai.kbe.services;

import de.htw.ai.kbe.storage.IUsersHandler;
import de.htw.ai.kbe.storage.UsersHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class AuthWebServiceTest extends JerseyTest {

   /* @Override
    protected Application configure() {
        return new ResourceConfig(AuthWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
            }
        });
    }*/
    @Before
    public void setUp() {
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new UsersHandler()).to(IUsersHandler.class);
            }
        });
    }


/*
    @Test
    public void requestWithoutUserInDbReturns403() {
        Response response = target("/auth?userId=userNotInDB").request().get();
        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void requestOfaUserWithoutTokenReturnsTokenAnd200() {
        Response response = target("/auth?userId=mmuster").request().get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void requestOfaUserWithTokenInDbReturns204() {
        Response response = target("/auth?userId=eschueler").request().get();
        Assert.assertEquals(204, response.getStatus());

    }*/
}