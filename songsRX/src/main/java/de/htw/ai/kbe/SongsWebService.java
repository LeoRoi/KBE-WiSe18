package de.htw.ai.kbe;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.storage.iSongsHandler;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@Path("/songs")
public class SongsWebService {
//    @Inject
    private iSongsHandler handler;

    @Context
    UriInfo uriInfo;

    @Inject
    public SongsWebService(iSongsHandler songsHandler) {
        super();
        handler = songsHandler;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Song> getAllSongs() {
        System.out.println("getAllSongs");
        return handler.getAllSongs();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Song getSong(@PathParam("id") int id) {
        System.out.println("getSong " + id);
        return handler.getSong(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSong(@PathParam("id") int id) {
        System.out.println("deleteSong " + id);

        if(handler.deleteSong(id)) {
            return Response.status(204).build();
        } else {
            return Response.status(404).build();
        }
    }
}
