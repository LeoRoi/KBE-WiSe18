package de.htw.ai.kbe.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import de.htw.ai.kbe.data.Song;

public class Utils {
    private Utils() {
    }
    // private constructor to avoid unnecessary instantiation of the class

    /**
     * [{"id":10,"title":"7 Years","artist":"Lukas Graham","album":"Lukas Graham (Blue Album)","released":2015},{"id":9,"title":"Private Show","artist":"Britney Spears","album":"Glory","released":2016},{"id":8,"title":"No","artist":"Meghan Trainor","album":"Thank You","released":2016},{"id":7,"title":"i hate u, i love u","artist":"Gnash","album":"Top Hits 2017","released":2017},{"id":6,"title":"I Took a Pill in Ibiza","artist":"Mike Posner","album":"At Night, Alone.","released":2016},{"id":5,"title":"Bad Things","artist":"Camila Cabello, Machine Gun Kelly","album":"Bloom","released":2017},{"id":4,"title":"Ghostbusters (I'm not a fraid)","artist":"Fall Out Boy, Missy Elliott","album":"Ghostbusters","released":2016},
     * {"id":3,"title":"Team","artist":"Iggy Azalea","album":null,"released":2016},
     * {"id":2,"title":"Mom","artist":"Meghan Trainor, Kelli Trainor","album":"Thank You","released":2016},
     * {"id":1,"title":"Can’t Stop the Feeling","artist":"Justin Timberlake","album":"Trolls","released":2016},
     * {"id":11,"title":"1. Song","artist":"1. Kuenstler","album":null,"released":1111}]
     */
    public static List<Song> loadSongs() {
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
