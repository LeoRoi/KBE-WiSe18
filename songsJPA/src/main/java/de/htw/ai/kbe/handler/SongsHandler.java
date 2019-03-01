package de.htw.ai.kbe.handler;

import de.htw.ai.kbe.entity.Song;

import static de.htw.ai.kbe.utils.Utils.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SongsHandler implements ISongsHandler {
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

    public SongsHandler() {
        this(jsonToSongsList("songs10.json"));
//        System.out.println("default constructor OK");
    }

    public SongsHandler(List<Song> songs) {
        this.storage = new ConcurrentHashMap<Integer, Song>();
        this.counter = new AtomicInteger();

        for (Song song : songs) {
            storage.put(song.getId(), song);
        }

        counter.set(songs.size());
//        printAllSongs();
//        System.out.println("SongsHandler init OK");
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
