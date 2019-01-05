package de.htw.ai.kbe.api;

import de.htw.ai.kbe.entity.Song;
import de.htw.ai.kbe.handler.IPlaylistsHandler;
import de.htw.ai.kbe.handler.ISongsHandler;
import de.htw.ai.kbe.handler.IUsersHandler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/songLists")
public class PlaylistsService {

    @Inject
    IUsersHandler usersHandler;

    @Inject
    ISongsHandler songsHandler;

    @Inject
    IPlaylistsHandler playlistsHandler;

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserPlaylists(@PathParam("userId") String uid) {
        System.out.println("getAllSongs");
        return Response.ok().build();
    }

    @GET
    @Path("/{listId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPlaylists(@PathParam("listId") String uid) {
        System.out.println("getAllSongs");
        return Response.ok().build();
    }
}
