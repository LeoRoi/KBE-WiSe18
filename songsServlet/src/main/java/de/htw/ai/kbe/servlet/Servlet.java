package de.htw.ai.kbe.servlet;

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
    }

    String getJsonPath() {
        return jsonPath;
    }

    Queue<Song> getSongs() {
        return songs;
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
    public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getContentType() == null) {
                sendResponse(response, 406, "Content type is not declared!");
                return;
            }
            if (request.getContentType().equals(JSON_CONTENT_TYPE)) {
                String requestedContentType = request.getHeader("Content-Type");
                if (requestedContentType.equals("") || requestedContentType.equals("null")) {
                    System.out.println("if (requestedContentType.equals(\"\") || requestedContentType.equals(\"null\")) {");
                    sendResponse(response, 406, "Could not interpret given header!");
                } else {
                    Song newSong = objectMapper.readValue(request.getInputStream(), new TypeReference<Song>(){});
                    newSong.setId(counter.incrementAndGet());
                    songs.add(newSong);
                    response.addHeader("Location", "http://localhost:8080/songsServlet?songId=" + counter.get());
                    sendResponse(response, 201, "New " + newSong.toString());
                }
            } else {
                sendResponse(response, 406, "Could not interpret given content type!");
            }
        } catch (IOException e) {
            sendResponse(response, 406, "Could not interpret JSON body!");
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