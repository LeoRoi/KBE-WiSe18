package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Song;

import java.util.Collection;

public interface ISongsHandler {
    Song getSong(int id);
    Collection<Song> getAllSongs();
    int addSong(Song song);
    boolean updateSong(int id, Song song);
    boolean deleteSong(int id);
}
