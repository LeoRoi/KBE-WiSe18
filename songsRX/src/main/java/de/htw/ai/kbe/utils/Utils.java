package de.htw.ai.kbe.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.data.User;

public class Utils {
    private Utils() {}

    public static List<Song> loadTestSongs() {
        List<Song> songs = new ArrayList<>();

        songs.add(new Song(0, "Black", "Stones", "Best", 1990));
        songs.add(new Song(1, "Can’t Stop the Feeling", "Justin Timberlake", "Trolls", 2016));
        songs.add(new Song(22, "Sun", "God", "Trans", -11990));
        songs.add(new Song(3, "Team", "Iggy", "Baby", 2015));

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

    // https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
    public static List<Song> jsonToSongsList(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream is = classloader.getResourceAsStream(filename)) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<User> readJSONToUsers(String filename) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<User>) objectMapper.readValue(is, new TypeReference<List<User>>() {
            });
        }
    }

    public static String generateToken() {
        String token = "";
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            token += (char) (random.nextInt(25) + 'a');
        }
        return token;
    }
}
