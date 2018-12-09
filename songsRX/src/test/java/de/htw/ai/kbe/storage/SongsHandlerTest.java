package de.htw.ai.kbe.storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SongsHandlerTest {
    SongsHandler handler;

    @Before
    public void setUp() throws Exception {
//        handler = new SongsHandler();
    }

    @Test
    public void init() {
        handler = new SongsHandler("songs.json");
        System.out.println(handler.getAllSongs());
    }

    @Test
    public void getSong() {
    }

    @Test
    public void getAllSongs() {
    }

    @Test
    public void addSong() {
    }

    @Test
    public void updateSong() {
    }

    @Test
    public void deleteSong() {
    }
}