package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;

import static de.htw.ai.kbe.utils.Utils.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SongsHandler implements ISongsHandler {
    private Map<Integer, Song> storage;
    private AtomicInteger counter;

    public Map<Integer, Song> getStorage() {
        return storage;
    }
    public AtomicInteger getCounter() {
        return counter;
    }


    public SongsHandler() {
        this.storage = new ConcurrentHashMap<>();
        this.counter = new AtomicInteger();
        initJson();
    }

    /*void init() {
        List<Song> songs = loadSongs();

        for (Song song : songs) {
            de.htw.ai.kbe.storage.put(counter.getAndIncrement(), song);
        }
    }*/

    private void initJson() {
        List<Song> songs;
        ClassLoader classLoader = getClass().getClassLoader();
        String pathToSongs = Objects.requireNonNull(classLoader.getResource("songs.json")).getPath();

        try {
            songs = readJSONToSongs(pathToSongs);

            for(Song song : songs) {
                addSong(song);
            }
            counter.set(storage.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*void init() {
        List<Song> songs;

        try {
            songs = readJSONToSongs("songs.json");  //TODO fix path to songs.json

            for(Song song : songs) {
                de.htw.ai.kbe.storage.put(counter.getAndIncrement(), song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public Song getSong(int id) {
        return storage.get(id);
    }

    public Collection<Song> getAllSongs() {
        return storage.values();
    }

    public int addSong(Song song) {
        int newSongId = counter.incrementAndGet();
        song.setId(newSongId);
        storage.put(newSongId, song);
        return newSongId;
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

    public boolean deleteSong(int id) {
        Song song = storage.remove(id);

        if (song != null) {
            return true;
        } else {
            return false;
        }
    }
}
