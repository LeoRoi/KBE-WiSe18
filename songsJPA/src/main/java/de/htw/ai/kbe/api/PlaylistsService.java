package de.htw.ai.kbe.api;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.Song;
import de.htw.ai.kbe.entity.User;
import de.htw.ai.kbe.handler.IPlaylistsHandler;
import de.htw.ai.kbe.handler.ISongsHandler;
import de.htw.ai.kbe.handler.IUsersHandler;
import sun.reflect.annotation.ExceptionProxy;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Path("/songLists")
public class PlaylistsService {

    @Inject
    IUsersHandler usersHandler;

    @Inject
    ISongsHandler songsHandler;

    @Inject
    IPlaylistsHandler playlistsHandler;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserPlaylists(@QueryParam("userId") String uid) {
        System.out.println("getPlaylistsByUserId");
        List<Playlist> playlistList;
        User owner;

        owner = usersHandler.getUserByUserId(uid);
        boolean userAndOwnerIdentical = usersHandler.getCurrentUser().getUserId().equals(uid);
        if (userAndOwnerIdentical) {
            playlistList = playlistsHandler.getUsersPlaylists(owner);
        } else {
            playlistList = playlistsHandler.getUsersPublicPlaylists(owner);
        }

        if (playlistList != null) {
            //needed for header accept application/xml
            GenericEntity<List<Playlist>> genericEntity = new GenericEntity<List<Playlist>>(playlistList) {
            };
            return Response
                    .status(200)
                    .entity(genericEntity)
                    .build();
        } else {
            return Response
                    .status(404)
                    .build();
        }
    }

    @GET
    @Path("/{listId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPlaylist(@PathParam("listId") int listId) {
        System.out.println("getPlaylist");
        Playlist playlist = playlistsHandler.getUserPlaylistById(listId);
        if (playlist == null) {
            return Response
                    .status(404, "Playlist not found"
                    ).build();
        }

        boolean userAndOwnerIdentical = playlist.getOwner().getId().equals(usersHandler.getCurrentUser().getId());
        boolean publicPlaylist = playlist.isOpen();
        if (userAndOwnerIdentical || publicPlaylist) {
            return Response
                    .status(200)
                    .entity(playlist)
                    .build();
        } else {
            return Response
                    .status(403, "Private Playlist of another User")
                    .build();
        }


    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response postPlaylist(@Context UriInfo uriInfo, Playlist playlist) {
        System.out.println("postPlaylist");
        UriBuilder uriBuilder;
        int cntOfSongsInDb = 0;

        Collection<Song> allSongs = songsHandler.getAllSongs();
        if (allSongs == null) {
            return Response.status(404, "No Playlists found").build();
        }

        //Search for Songs in DB
        for (Song song : playlist.getContent()) {
            for (Song songDB : allSongs) {
                boolean songInDB = songDB.getTitle().equals(song.getTitle());
                System.out.println("title=" + song.getTitle());
                System.out.println("titleDB=" + songDB.getTitle());
                if (songInDB) {
                    cntOfSongsInDb++;
                    break;
                }
            }
        }

        boolean allSongsInDB = cntOfSongsInDb == playlist.getContent().size();
        if (allSongsInDB) {
            uriBuilder = uriInfo.getAbsolutePathBuilder();
            int locationOfSong = playlistsHandler.addPlaylist(playlist);
            uriBuilder.path(Integer.toString(locationOfSong));
            return Response
                    .status(200)
                    .contentLocation(uriBuilder.build())
                    .build();
        }
        return Response
                .status(404, "This song is not in the DB")
                .build();
    }

    @DELETE
    @Path("/{listId}")
    public Response deletePlaylist(@PathParam("listId") int listId) {
        System.out.println("deletePlaylist");
        Playlist playlist = playlistsHandler.getUserPlaylistById(listId);
        if (playlist == null) {
            return Response.status(404, "Playlist not found.").build();
        }

        boolean userAndOwnerIdentical = playlist.getOwner().getId().equals(usersHandler.getCurrentUser().getId());
        if (userAndOwnerIdentical) {
            boolean deleted = playlistsHandler.deletePlaylist(listId);
            if (deleted) {
                return Response.status(204).build();
            } else {
                return Response.status(400, "Couldn't delete Playlist").build();
            }
        } else {
            return Response
                    .status(403, "The Playlist you have requested is not public.")
                    .build();
        }
    }

}
