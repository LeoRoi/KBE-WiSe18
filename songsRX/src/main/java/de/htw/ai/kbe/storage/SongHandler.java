package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SongHandler implements iSongsHandler {
    private Map<Integer, Song> storage;
    private AtomicInteger counter;

    public SongHandler() {
        this.storage = new ConcurrentHashMap<Integer, Song>();
        this.counter = new AtomicInteger();
    }

    public Song getSong(int id) {
        return storage.get(id);
    }

    public Collection<Song> getAllSongs() {
        return storage.values();
    }

    public void addSong(Song song) {
        storage.put(counter.incrementAndGet(), song);
    }

    public boolean updateSong(Song newSong) {
        Song song = storage.remove(newSong.getId());

        if (song != null) {
            storage.put(newSong.getId(), newSong);
            return true;
        } else {
            return false;
        }
    }

    public Song deleteSong(int id) {
        return storage.remove(id);
    }
}
