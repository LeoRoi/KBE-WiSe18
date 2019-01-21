package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.User;

import java.util.List;

public interface IPlaylistsHandler {

    @SuppressWarnings("unchecked")
    List<Playlist> getUsersPlaylists(User owner);

    @SuppressWarnings("unchecked")
    List<Playlist> getUsersPublicPlaylists(User owner);

    Playlist getUserPlaylistById(int pid);
    int addPlaylist(Playlist playlist);
    boolean deletePlaylist(int pid);
}
