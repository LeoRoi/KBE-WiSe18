package de.htw.ai.kbe.storage;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.data.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;
import java.util.NoSuchElementException;

import static de.htw.ai.kbe.utils.Constants.*;
import static de.htw.ai.kbe.utils.Utils.jsonToSongsList;

public class SongsDaoTest {
    static SongsHandler testHandler;
    static ISongsHandler handler;
    static EntityManagerFactory emf;
    static EntityManager em;

    @BeforeClass
    public static void setUp() {
        testHandler = new SongsHandler(jsonToSongsList("songs10.json"));
        emf = Persistence.createEntityManagerFactory(TEST_PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        handler = new SongsDaoEm(em);
    }

    @AfterClass
    public static void close() {
        emf.close();
    }

    @Test
    public void init() {
        System.out.println("SongsDaoEmfTest.init.all=" + handler.getAllSongs());
        System.out.println("SongsDaoEmfTest.init.all.size=" + handler.getAllSongs().size());
        assert (testHandler.getStorage().size() == handler.getAllSongs().size());
    }

    @Test
    public void getSong() {
        int id = 2;
        Song testSong = testHandler.getSong(id);
        Song song = handler.getSong(id);

        System.out.println("SongsDaoEmfTest.getSong.test=" + testSong);
        System.out.println("SongsDaoEmfTest.getSong.real=" + song);

        assertEquals(testSong.toString(), song.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void getNonExistingSong() {
        int id = 2222;
        Song song = handler.getSong(id);
        System.out.println("SongsDaoEmfTest.getSong.real=" + song);
        assertNull(song);
    }

    @Test
    public void addAndDeleteSong() {
        String myTitle = "christmas";
//        Song newSong = new Song(myTitle, "who s the singer??", "end of the year", 1980);
//        handler.addSong(newSong);
//        handler.addSong(newSong);
//        assertEquals(12, handler.getAllSongs().size());

        Query q = em.createQuery("SELECT s FROM Song s where s.title = " + myTitle);
        @SuppressWarnings("unchecked")
        List<Song> songs = q.getResultList();
        for(Song song : songs) {
            int id = song.getId();
            em.remove(em.find(Song.class, id));
        }
        assertEquals(10, handler.getAllSongs().size());
    }
}