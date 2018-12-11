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

    public int getStorageSize() {
        return storage.size();
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public int getCounterValue() {
        return counter.get();
    }

    public SongsHandler() {
        this.storage = new ConcurrentHashMap<Integer, Song>();
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

    public SongsHandler(String jsonFile) {
        this.storage = new ConcurrentHashMap<Integer, Song>();
        this.counter = new AtomicInteger();
        init(jsonFile);
    }

    void init(String jsonName) {
        counter.set(0);

        for (Song song : jsonToSongsList(jsonName)) {
            storage.put(counter.getAndIncrement(), song);
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
    public void addSong(Song newSong) {
        storage.put(counter.incrementAndGet(), newSong);
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
