package de.htw.ai.kbe.utils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import de.htw.ai.kbe.data.Song;
import de.htw.ai.kbe.data.User;

public class Utils {
    private Utils() {}

    /**
     * [{"id":10,"title":"7 Years","artist":"Lukas Graham","album":"Lukas Graham (Blue Album)","released":2015},{"id":9,"title":"Private Show","artist":"Britney Spears","album":"Glory","released":2016},{"id":8,"title":"No","artist":"Meghan Trainor","album":"Thank You","released":2016},{"id":7,"title":"i hate u, i love u","artist":"Gnash","album":"Top Hits 2017","released":2017},{"id":6,"title":"I Took a Pill in Ibiza","artist":"Mike Posner","album":"At Night, Alone.","released":2016},{"id":5,"title":"Bad Things","artist":"Camila Cabello, Machine Gun Kelly","album":"Bloom","released":2017},{"id":4,"title":"Ghostbusters (I'm not a fraid)","artist":"Fall Out Boy, Missy Elliott","album":"Ghostbusters","released":2016},
     * {"id":3,"title":"Team","artist":"Iggy Azalea","album":null,"released":2016},
     * {"id":2,"title":"Mom","artist":"Meghan Trainor, Kelli Trainor","album":"Thank You","released":2016},
     * {"id":1,"title":"Can’t Stop the Feeling","artist":"Justin Timberlake","album":"Trolls","released":2016},
     * {"id":11,"title":"1. Song","artist":"1. Kuenstler","album":null,"released":1111}]
     */
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

    //https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
    public static List<User> jsonToUserList(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = classloader.getResourceAsStream(filename)) {
            return (List<User>) objectMapper.readValue(is, new TypeReference<List<User>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
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
