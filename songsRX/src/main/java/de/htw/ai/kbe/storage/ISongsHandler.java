package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import java.util.Collection;

public interface ISongsHandler {
    Song getSong(int id);
    Collection<Song> getAllSongs();
    int addSong(Song song);
    boolean updateSong(Song song);
    boolean deleteSong(int id);
}
