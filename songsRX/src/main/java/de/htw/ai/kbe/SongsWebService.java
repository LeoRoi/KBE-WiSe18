package de.htw.ai.kbe;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.storage.SongHandler;
import de.htw.ai.kbe.storage.iSongsHandler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/songs")
public class SongsWebService {
    private iSongsHandler handler;

    @Inject
    public SongsWebService(iSongsHandler songsHandler) {
        super();
        handler = songsHandler;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Song> getAllSongs() {
        System.out.println("getAllSongs!");
        return handler.getAllSongs();
    }
}
