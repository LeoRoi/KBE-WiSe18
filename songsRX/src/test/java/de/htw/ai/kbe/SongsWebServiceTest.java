package de.htw.ai.kbe;

import de.htw.ai.kbe.services.SongsWebService;
import de.htw.ai.kbe.storage.SongsHandler;
import de.htw.ai.kbe.storage.iSongsHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;

public class SongsWebServiceTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(
                new AbstractBinder(){
                    @Override
                    protected void configure() {
                        bind(SongsHandler.class).to(iSongsHandler.class).in(Singleton.class);
                    }
                });
    }
}