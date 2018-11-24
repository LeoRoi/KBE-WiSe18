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

import static de.htw.ai.kbe.servlet.Constants.*;

public class Servlet extends HttpServlet {
    private String jsonPath;
//    private List<Song> songs;
    private Queue<Song> songs;
    private AtomicInteger counter;
    private ObjectMapper objectMapper;
    private Utils utils;

    public Servlet() {
//        jsonPath = "";
        songs = new ConcurrentLinkedQueue<>();
        counter = new AtomicInteger();
        objectMapper = new ObjectMapper();
        utils = new Utils();
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
    public void init(ServletConfig servletConfig) {
        this.jsonPath = servletConfig.getInitParameter("jsonPath");
        System.out.println("jsonPath = " + jsonPath);

        try {
            songs.addAll(utils.readJSONToSongs(jsonPath));
            System.out.println("songs = " + songs.toString());
            counter.set(songs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (utils.requestAcceptHeaderOk(request.getHeader("accept"))) {
                Map<String, String> headerParams = utils.getRequestParams(request);
//                String headerAll = headerParams.get("all");

//                if (headerAll != null && headerAll.equals("")) {
                if (headerParams.get("all") != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(APPLICATION_JSON);
                    response.setCharacterEncoding(ENCODING);
                    response.getWriter().println(objectMapper.writeValueAsString(songs));
//                    System.out.println(objectMapper.writeValueAsString(songs));
                } else if (headerParams.get("songId") != null) {
                    int id = Integer.parseInt(headerParams.get("songId"));

                    for (Song song : songs) {
                        if (song.getId() == id) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void destroy() {
//        try {
//            objectMapper.writeValue(new File(jsonPath), songs);
//            System.out.println("Servlet Exited!");
//        } catch (UnsupportedOperationException | IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
