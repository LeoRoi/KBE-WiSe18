package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static de.htw.ai.kbe.servlet.Constants.*;
import static de.htw.ai.kbe.servlet.Utils.*;

public class Servlet extends HttpServlet {
    private String jsonPath;
    private Queue<Song> songs;
    private AtomicInteger counter;
    private ObjectMapper objectMapper;

    public Servlet() {
        songs = new ConcurrentLinkedQueue<>();
        counter = new AtomicInteger();

        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    String getJsonPath() {
        return jsonPath;
    }

    Queue<Song> getSongs() {
        return songs;
    }

    AtomicInteger getCounter() {
        return counter;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        jsonPath = servletConfig.getInitParameter("jsonPath");

        try {
            songs.addAll(readJSONToSongs(jsonPath));
            counter.set(songs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Servlet is up and running!");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (requestAcceptHeaderOk(request.getHeader("accept"))) {
                Map<String, String> headerParams = getRequestParams(request);

                if (headerParams.get("all") != null) {
                    sendResponse(response, 200, objectMapper.writeValueAsString(songs));
                } else if (headerParams.get("songId") != null) {
                    if (!isInteger(headerParams.get("songId"))) {
                        sendResponse(response, 400, "Could not interpret given id!");
                        return;
                    }

                    int id = Integer.parseInt(headerParams.get("songId"));
                    if (id > counter.get()) {
                        sendResponse(response, 404, "Song with <id=" + id + "> not found!");
                    } else {
                        for (Song song : songs) {
                            if (song.getId() == id) {
                                sendResponse(response, 200, objectMapper.writeValueAsString(song));
                                break;
                            }
                        }
                    }
                } else {
                    sendResponse(response, 406, "Could not interpret given parameters!");
                }
            } else {
                sendResponse(response, 406, "Could not interpret given header <" + request.getHeader("accept") + ">!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getContentType().equals(Constants.JSON_CONTENT_TYPE)) {
            try (ServletInputStream inputStream = request.getInputStream()) {
                Map<String, Object> jsonMap =
                        objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>(){});
                if (jsonStructureOk(jsonMap)) {
                    Song newSong = objectMapper.convertValue(jsonMap, new TypeReference<Song>() {});
                    newSong.setId(counter.incrementAndGet());
                    songs.add(newSong);
                    try {
                        response.setHeader("Location", "http://localhost:8080/songsServlet?songId="+counter);
                        response.setStatus(201);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendError(400, "The payload has not the right JSON structure.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendError(400, "Only JSON format is accepted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        try {
            objectMapper.writeValue(new File(jsonPath), songs);
            System.out.println("Servlet terminated after saving songs to <" + jsonPath + ">!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}