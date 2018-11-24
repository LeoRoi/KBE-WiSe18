package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static de.htw.ai.kbe.servlet.Utils.*;

public class Servlet extends HttpServlet {
    static final public String APPLICATION_JSON = "application/json";
    static final String ENCODING = "UTF-8";

    private String jsonPath;
//    private List<Song> songs;
    private Queue<Song> songs = new ConcurrentLinkedQueue<>();
    private AtomicInteger counter = new AtomicInteger();
    private ObjectMapper objectMapper = new ObjectMapper();

    public Servlet() {
        jsonPath = "";
        songs = new ConcurrentLinkedQueue<>();
        counter = new AtomicInteger();
        objectMapper = new ObjectMapper();
    }

    public String getJsonPath() {
        return jsonPath;
    }
//    public List<Song> getSongs() {
//        return songs;

//    }

    public Queue<Song> getSongs() {
        return songs;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.jsonPath = servletConfig.getInitParameter("jsonPath");

        try {
//            List<Song> songsNotAtomic = readJSONToSongs(jsonPath);
//            songs = convertToAtomic(songsNotAtomic);
            songs.addAll(readJSONToSongs(jsonPath));
            counter.set(songs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if(requestAcceptHeaderOk(request.getHeader("accept"))) {
            Map<String, String> headerParams = getRequestParams(request);

            if(headerParams.get("all") != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType(APPLICATION_JSON);
                response.setCharacterEncoding(ENCODING);
                response.getWriter().println(objectMapper.writeValueAsString(songs));
            } else if(headerParams.get("songId") != null) {
                int id = Integer.parseInt(headerParams.get("songId"));

                for(Song song : songs) {
                    if(song.getId() == id) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType(APPLICATION_JSON);
                        response.setCharacterEncoding(ENCODING);
                        response.getWriter().println(objectMapper.writeValueAsString(song));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.setContentType(APPLICATION_JSON);
                        response.setCharacterEncoding(ENCODING);
                        response.getWriter().println("Song not found!");
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.setContentType(APPLICATION_JSON);
                response.setCharacterEncoding(ENCODING);
                response.getWriter().println("Wrong parameters!");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.setContentType(APPLICATION_JSON);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().println("Wrong header!");
        }
    }
}
