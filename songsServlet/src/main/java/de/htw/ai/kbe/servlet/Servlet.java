package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

public class Servlet extends HttpServlet {
    private String jsonPath;
    private Queue<Song> songs;
    private AtomicInteger counter;
    private ObjectMapper objectMapper;
    private Utils utils;

    public Servlet() {
        songs = new ConcurrentLinkedQueue<>();     // concurrentLinkedQueue is concurrent and synchronized unlike synchronizedList(List)
        counter = new AtomicInteger();             // Counter of the song IDs, atomic for Thread safety
        objectMapper = new ObjectMapper();
        utils = new Utils();
    }

    String getJsonPath() {
        return jsonPath;
    }

    Queue<Song> getSongs() {
        return songs;
    }

    //TODO kann weg, es gibt schon eine get() in java.util.concurrent.atomic.AtomicInteger
    AtomicInteger getCounter() {
        return counter;
    }

    /**
     * loads the external songs.json file and sets the songId counter
     * @param servletConfig
     */
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

    /**
     * Responds to a http get request with a http response including either all songs
     * or the requested song.
     * @param request
     * @param response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (utils.requestAcceptHeaderOk(request.getHeader("accept"))) {
                Map<String, String> headerParams = utils.getRequestParams(request);

                if (headerParams.get("all") != null) {
                    utils.sendResponse(response, 200, objectMapper.writeValueAsString(songs));
                } else if (headerParams.get("songId") != null) {
                    if (!utils.isInteger(headerParams.get("songId"))) {
                        utils.sendResponse(response, 400, "Could not interpret given id!");
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


    /** generate new ID for a song and store it in the songs list. respond to client with new id in location header.
     * Only accepts json as payload.
     * @Source https://stackoverflow.com/questions/14291027/what-is-the-use-of-response-setcontenttypetext-html-in-servlet#14291042
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getContentType().equals("application/json")) {
            try (ServletInputStream inputStream = request.getInputStream()) {
                // TODO here has to be a check for the correct json structure
                if (true) {
                    Song song = (Song) objectMapper.readValue(inputStream, new TypeReference<Song>() {});
                    song.setId(counter.getAndIncrement());
                    songs.add(song);
                    try {
                        response.setHeader("Location", "http://localhost:8080/songsServlet?songId="+counter);
                        response.setStatus(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    response.sendError(400, "The payload has not the right structure.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // TODO you don't need to set the content type for sendError(), right?
                response.sendError(400, "Only JSON format is accepted");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void destroy() {
        try {
            utils.writeSongsToJSON((List<Song>) getSongs(), jsonPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
