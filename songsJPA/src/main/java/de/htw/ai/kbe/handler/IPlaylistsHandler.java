package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Playlist;
import de.htw.ai.kbe.entity.Song;
import de.htw.ai.kbe.entity.User;

import java.util.List;

public interface IPlaylistsHandler {
    List<Playlist> getUserPlaylists(User user);
    Playlist getUserPlaylistsWithId(User user, int pid);
    int addPlaylist(Playlist playlist);
    boolean deletePlaylist(int pid);
}
