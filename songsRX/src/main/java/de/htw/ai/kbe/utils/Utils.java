package de.htw.ai.kbe.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import de.htw.ai.kbe.data.Song;

public class Utils {
    private Utils() {}

    public static List<Song> loadTestSongs() {
        List<Song> songs = new ArrayList<>();

        Song song = new Song();
        song.setId(0);
        song.setArtist("Stone");
        song.setAlbum("Best of the best");
        song.setReleased(1990);
        song.setTitle("Black");
        songs.add(song);

        song = new Song();
        song.setId(1);
        song.setTitle("the feeling");
        song.setArtist("justin");
        song.setAlbum("troll");
        song.setReleased(2016);
        songs.add(song);

        song = new Song();
        song.setId(2);
        song.setTitle("mom");
        song.setArtist("megan");
        song.setAlbum("thx");
        song.setReleased(2018);
        songs.add(song);

        songs.add(new Song(3, "team", "iggy", "null", 2015));

        return songs;
    }

    // from jaxbjackson
    public static List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }
}
