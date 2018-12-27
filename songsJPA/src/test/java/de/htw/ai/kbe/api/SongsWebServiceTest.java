package de.htw.ai.kbe.api;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.storage.SongsDao;
import de.htw.ai.kbe.storage.ISongsHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class SongsWebServiceTest extends JerseyTest {

    private Song song;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        song = new Song();
        song.setId(3);
        song.setAlbum("Best of 2000");
        song.setArtist("Helene Fischer");
        song.setReleased(2000);
        song.setTitle("Breathless through the night");
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(SongsDao.class).to(ISongsHandler.class).in(Singleton.class);
            }
        });
    }

    @Test
    public void putSongWithExistingIdShouldReturn204() {
        Response response = target("/songs/3").request().put(Entity.json(song));
        Assert.assertEquals(204, response.getStatus());
    }

    @Test
    public void putSongWithNonExistingIdShouldReturn404() {
        song.setId(100);
        Response response = target("/songs/100").request().put(Entity.json(song));
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void putSongWithXmlMediaTypeShouldReturn204() {
        Response response = target("/songs/3").request().put(Entity.xml(song));
        Assert.assertEquals(204, response.getStatus());
    }

    @Test
    public void putSongWithMalformedJsonShouldReturn406() {
        song.setTitle(null);
        Response response = target("/songs/3").request().put(Entity.json(song));
        Assert.assertEquals(406, response.getStatus());
    }

    @Test
    public void putSongWithIdMismatchShouldReturn406() {
        song.setId(3);
        Response response = target("/songs/50").request().put(Entity.json(song));
        Assert.assertEquals(406, response.getStatus());
    }


}