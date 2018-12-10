package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SongsHandlerTest {
    SongsHandler handler;
    SongsHandler testHandler;

    @Before
    public void setUp() {
        testHandler = new SongsHandler();
        handler = new SongsHandler("songs.json");
    }

    @Test
    public void initFails() {
        SongsHandler h = new SongsHandler("s0ngs.json");
        assertTrue(h.getStorage().isEmpty());
        System.out.println("songs loaded:\n" + h.getAllSongs());
    }

    @Test
    public void initSuccessful() {
        int size = handler.getStorage().size();
        assert(size == 11);
        assertTrue(handler.getStorage().size() == handler.getCounter().get());
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

        System.out.println("Song <1> from the file should be equal to the song <1> from the test:");
        System.out.println("Song <1> from the file: " + songFromFile);
        System.out.println("Song <1> from the test: " + testSong);

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
        int sizeBeforeAddingNewSong = handler.getStorage().size();
        handler.addSong(newSong);

        assertTrue(handler.getStorage().size() == sizeBeforeAddingNewSong+1);
        System.out.println("storage(" + handler.getCounter().get() + ") = " + newSong);
    }

    @Test
    public void updateSong() {
        int id = 11;
        System.out.println("Song(" + id + ") before: " + handler.getSong(id));

        Song newSong = new Song(22, "Sun", "God", "Trans", -11990);
        handler.updateSong(id, newSong);

        assertEquals(testHandler.getStorage().get(22).toString(), handler.getSong(id).toString());
        System.out.println("Song(" + id + ") after: " + handler.getSong(id));
    }

    @Test
    public void deleteSong() {
        int sizeBefore = handler.getStorage().size();
        handler.deleteSong(11);
        handler.deleteSong(1);
        assert(9 == sizeBefore-2);
    }
}