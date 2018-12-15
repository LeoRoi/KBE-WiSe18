package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;
import org.junit.Before;
import org.junit.Test;

import static de.htw.ai.kbe.utils.Utils.*;
import static org.junit.Assert.*;

public class SongsHandlerTest {
    SongsHandler handler;
    SongsHandlerT testHandler;

    @Before
    public void setUp() {
        testHandler = new SongsHandlerT();
        handler = new SongsHandler(jsonToSongsList("songs11.json"));
    }

    @Test
    public void initFails() {
        SongsHandler h = new SongsHandler(jsonToSongsList("s0ngs.json"));
        assert(h.getStorage().size() == 4);
        System.out.println("songs loaded:\n" + h.getAllSongs());
    }

    @Test
    public void initWithTenSongs() {
        SongsHandler h = new SongsHandler();
        System.out.println("songs loaded:\n" + h.getAllSongs());
        assert(h.getStorage().size() == 10);
    }

    @Test
    public void initWithElevenSongs() {
        handler.printAllSongs();
        System.out.println("counter = " + handler.getCounterValue());
        int size = handler.getStorage().size();

        assert(11 == size);
        assert(size == handler.getCounterValue());

        System.out.println("map size = " + size);
        System.out.println("counter = " + handler.getCounterValue());
        System.out.println(size + " songs loaded:\n" + handler.getAllSongs());
    }

    @Test
    public void initTest() {
        assert(testHandler.getStorage().size() == 4);
        System.out.println("songs loaded:\n" + testHandler.getAllSongs());
    }

    // song 1 is the same in both sources
    @Test
    public void getSongSuccessful() {
        Song songFromFile = handler.getSong(1);
        Song testSong = testHandler.getSong(1);

        System.out.println(">> Song from file and test should be equal:");
        System.out.println(songFromFile);
        System.out.println(testSong);

        assertEquals(testSong.getTitle(), songFromFile.getTitle());
        assertEquals(testSong.getArtist(), songFromFile.getArtist());
        assertEquals(testSong.getAlbum(), songFromFile.getAlbum());
        assertEquals(testSong.getReleased(), songFromFile.getReleased());
    }

    @Test
    public void getSongFails() {
        Song songFromFile = handler.getSong(1111);
        assertNull(songFromFile);
    }

    @Test
    public void addSong() {
        Song newSong = new Song(0, "Black", "Bones", "Best", 1990);
        int sizeBefore = handler.getStorage().size();
        System.out.println("size before: " + sizeBefore);
        System.out.println("storage(" + sizeBefore + ") = " + handler.getSong(sizeBefore));
        System.out.println(handler.getAllSongs());
        System.out.println();

        handler.addSong(newSong);
        int newSize = handler.getStorage().size();
        System.out.println("storage(" + newSize + ") = " + handler.getSong(newSize));
        System.out.println("size after: " + handler.getStorage().size());
        System.out.println(handler.getAllSongs());
        assert(handler.getStorage().size() == sizeBefore+1);
    }

    @Test
    public void updateSongOk() {
        int lastId = handler.getStorageSize() - 1;
        System.out.println("last song song before: " + handler.getSong(lastId));
        Song newSong = new Song(22, "Sun", "God", "Trans", -11990);

        assertTrue(handler.updateSong(lastId, newSong));
        assertEquals(testHandler.getStorage().get(22).toString(), handler.getSong(lastId).toString());
        System.out.println("last song after = " + handler.getSong(lastId));
    }

    @Test
    public void updateSongFail() {
        int id = 1111;
        Song newSong = new Song(22, "Sun", "God", "Trans", -11990);
        assertFalse(handler.updateSong(id, newSong));
    }

    @Test
    public void deleteSongOk() {
        int sizeBefore = handler.getStorage().size();
        int id = sizeBefore-1;
        System.out.println("Song(" + id + ") before: " + handler.getSong(id));

        handler.deleteSong(id);
        handler.deleteSong(1);
        assert(9 == sizeBefore-2);

        System.out.println("Song(" + id + ") after: " + handler.getSong(id));
        assertNull(handler.getSong(id));
    }

    @Test
    public void deleteSongFail() {
        int sizeBefore = handler.getStorage().size();
        int id = 1111;

        assertFalse(handler.deleteSong(id));
        assert(sizeBefore == handler.getStorageSize());
    }
}