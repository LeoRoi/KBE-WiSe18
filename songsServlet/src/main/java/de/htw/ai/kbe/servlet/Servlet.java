package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
    private String jsonPath;
    private Queue<Song> songs;
    private AtomicInteger counter;
    private ObjectMapper objectMapper;
    private Utils utils;

    public Servlet() {
        songs = new ConcurrentLinkedQueue<>();
        counter = new AtomicInteger();
        objectMapper = new ObjectMapper();
        utils = new Utils();
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public Queue<Song> getSongs() {
        return songs;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        jsonPath = servletConfig.getInitParameter("jsonPath");

        try {
            songs.addAll(utils.readJSONToSongs(jsonPath));
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

                if (headerParams.get("all") != null) {
                    utils.sendResponse(response, 200, objectMapper.writeValueAsString(songs));
                } else if (headerParams.get("songId") != null) {
                    if (!utils.isInteger(headerParams.get("songId"))) {
                        utils.sendResponse(response, 200, "Could not interpret given id!");
                        return;
                    }

                    int id = Integer.parseInt(headerParams.get("songId"));
                    if (id > counter.get()) {
                        utils.sendResponse(response, 404, "Song with <id=" + id + "> not found!");
                    } else {
                        for (Song song : songs) {
                            if (song.getId() == id) {
                                utils.sendResponse(response, 200, objectMapper.writeValueAsString(song));
                                break;
                            }
                        }
                    }
                } else {
                    utils.sendResponse(response, 406, "Could not interpret given parameters!");
                }
            } else {
                utils.sendResponse(response, 406, "Could not interpret given header <" + request.getHeader("accept") + ">!");
            }
        } catch (IOException e) {
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
