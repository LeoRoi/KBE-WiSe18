package de.htw.ai.kbe.api;

import de.htw.ai.kbe.storage.IUsersHandler;
import de.htw.ai.kbe.storage.UsersHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class AuthWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(UsersHandler.class).to(IUsersHandler.class).in(Singleton.class);
            }
        });
    }


    @Test
    public void requestWithoutUserInDbReturns403() {
        Response response = target("/auth").queryParam("userId","UserNotInDB").request().get();
        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void requestOfaUserWithoutTokenReturnsTokenAnd200() {
        Response response = target("/auth").queryParam("userId","mmuster").request().get();
        //Response response = target("/auth?userId=mmuster").request().get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void requestOfaUserWithTokenInDbReturns200() {
        //first Request is needed to generate the token
        Response response1 = target("/auth").queryParam("userId","mmuster").request().get();
        //second Request is needed to verify the already existing token
        Response response2 = target("/auth").queryParam("userId", "mmuster").request().get();
        Assert.assertEquals(200, response2.getStatus());

    }
}