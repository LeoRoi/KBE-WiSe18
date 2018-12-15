package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static de.htw.ai.kbe.utils.Utils.*;

public class SongsHandlerT implements ISongsHandler {
    private Map<Integer, Song> storage;
    private AtomicInteger counter;

    public Map<Integer, Song> getStorage() {
        return storage;
    }

    public int getStorageSize() {
        return storage.size();
    }

    public int getCounterValue() {
        return counter.get();
    }

    public SongsHandlerT() {
        this.storage = new ConcurrentHashMap<>();
        this.counter = new AtomicInteger();
        initTest();
    }

    /*
    if no file name provided, load some hard coded songs
    do not init counter
    use id from payload for map
     */
    void initTest() {
        List<Song> songs = loadTestSongs();

        for (Song song : songs) {
            storage.put(song.getId(), song);
        }
    }

    public Song getSong(int id) {
        return storage.get(id);
    }

    public Collection<Song> getAllSongs() {
        return storage.values();
    }

    public void printSong(int i) {
        System.out.println(i + " = " + storage.get(i));
    }

    public void printAllSongs() {
        System.out.println(storage);
    }

    // id based on counter
    public int addSong(Song newSong) {
        storage.put(counter.incrementAndGet(), newSong);
        return counter.get();
    }

    // take id from url, update content according to the payload
    public boolean updateSong(int id, Song newSong) {
        Song song = storage.remove(id);

        if (song != null) {
            storage.put(id, newSong);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSong(int id) {
        Song song = storage.remove(id);

        if (song != null) {
            return true;
        } else {
            return false;
        }
    }
}
