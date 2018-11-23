package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
    private String jsonPath = "";
    private List<Song> songs;
    private AtomicInteger counter = new AtomicInteger();

    public String getJsonPath() {
        return jsonPath;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public Servlet() {
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.jsonPath = servletConfig.getInitParameter("jsonPath");

        try {
            songs = readJSONToSongs(jsonPath);
            counter.set(songs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

    }

    // from jaxbjackson
    static List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }
}
