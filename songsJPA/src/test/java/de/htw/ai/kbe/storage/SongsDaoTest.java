package de.htw.ai.kbe.storage;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static de.htw.ai.kbe.utils.Constants.*;
import static de.htw.ai.kbe.utils.Utils.jsonToSongsList;

public class SongsDaoTest {
    SongsHandler testHandler;
    ISongsHandler handler;
    EntityManagerFactory emf;

    @Before
    public void setUp() {
        testHandler = new SongsHandler(jsonToSongsList("songs10.json"));
        emf = Persistence.createEntityManagerFactory(TEST_PERSISTENCE_UNIT_NAME);
        handler = new SongsDao(emf);
    }

    @Test
    public void init() {
        assert(testHandler.getStorage().size() == handler.getAllSongs().size());
    }
}