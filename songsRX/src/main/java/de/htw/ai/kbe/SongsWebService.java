package de.htw.ai.kbe;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.storage.iSongsHandler;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/songs")
public class SongsWebService {
    private iSongsHandler handler;

    @Inject
    public SongsWebService(iSongsHandler songsHandler) {
        super();
        handler = songsHandler;
    }

    // curl -X GET -H "Accept: application/json" -v "http://localhost:8080/songsRX/rest/songs"
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Song> getAllSongs() {
        System.out.println("getSong!");
        return handler.getAllSongs();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Song getSong(@PathParam("id") int id) {
        System.out.println("getAllSongs!");
        return handler.getSong(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSong(@PathParam("id") int id) {
        System.out.println("deleteSong!");
        boolean result = handler.deleteSong(id);

        if(result) {
            return Response.status(200).build();
        } else {
            return Response.status(404).build();
        }
    }
}
