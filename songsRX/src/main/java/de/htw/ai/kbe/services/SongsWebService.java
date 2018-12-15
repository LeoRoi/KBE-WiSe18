package de.htw.ai.kbe.services;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.storage.ISongsHandler;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/songs")
public class SongsWebService {
//    @Inject
    private ISongsHandler handler;

    @Context
    UriInfo uriInfo;

    @Inject
    public SongsWebService(ISongsHandler songsHandler) {
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

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSong(Song song, @Context UriInfo uriInfo) {
        int idForURL = handler.addSong(song);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer.toString(idForURL));
        return Response.created(uriBuilder.build()).status(201).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response updateSong(@PathParam("id") int id, Song newSong) {
        if (newSong.getId() == id) {
            if (newSong.getTitle() != null) {
                if (handler.updateSong(id, newSong)) {
                    return Response.status(204).build();
                } else {
                    return Response.status(404, "Not found, the Song Id is not in the database.").build();
                }
            } else {
                return Response.status(406, "Not acceptable, the Song needs a title.").build();
            }
        } else {
            return Response.status(406, "Not acceptable, the url Id has to match the song Id").build();
        }
    }
}
