package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import static de.htw.ai.kbe.utils.Utils.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SongsHandler implements iSongsHandler {
    private Map<Integer, Song> storage;
    private AtomicInteger counter;

    public Map<Integer, Song> getStorage() {
        return storage;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public SongsHandler() {
        this.storage = new ConcurrentHashMap<Integer, Song>();
        this.counter = new AtomicInteger();
        initTest();
    }

    public SongsHandler(String jsonFile) {
        this.storage = new ConcurrentHashMap<Integer, Song>();
        this.counter = new AtomicInteger();

        init(jsonFile);
//        for (Song song : jsonToSongsList(jsonFile))
//            storage.put(counter.getAndIncrement(), song);
    }

    // if no file name provided, load some hard coded songs
    void initTest() {
        List<Song> songs = loadTestSongs();

        for (Song song : songs) {
            storage.put(song.getId(), song);
        }
    }

    void init(String jsonName) {
        for (Song song : jsonToSongsList(jsonName)) {
//            storage.put(counter.getAndIncrement(), song);
            storage.put(song.getId(), song);
        }
        counter.set(storage.size());
    }

    public Song getSong(int id) {
        return storage.get(id);
    }

    public Collection<Song> getAllSongs() {
        return storage.values();
    }

    // id based on counter
    public void addSong(Song song) {
        storage.put(counter.incrementAndGet(), song);
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
