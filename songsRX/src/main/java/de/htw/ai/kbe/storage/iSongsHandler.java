package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import java.util.Collection;

public interface iSongsHandler {
    Song getSong(int id);
    Collection<Song> getAllSongs();
    void addSong(Song song);
    boolean updateSong(Song song);
    boolean deleteSong(int id);
}
