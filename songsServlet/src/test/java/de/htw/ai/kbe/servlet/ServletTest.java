package de.htw.ai.kbe.servlet;

import static de.htw.ai.kbe.servlet.Constants.*;
import static de.htw.ai.kbe.servlet.Utils.stringOk;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.ServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ServletTest {
    private Servlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws ServletException {
        servlet = new Servlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        config = new MockServletConfig();
        config.addInitParameter("jsonPath", "songs.json");
        servlet.init(config); //throws ServletException

        objectMapper = new ObjectMapper();
    }

    @Test
    public void initPath() {
        assert(stringOk(servlet.getJsonPath()));
        System.out.println("jsonPath = " + servlet.getJsonPath());
    }

    @Test
    public void initSongs() {
        assert(!servlet.getSongs().isEmpty());
        System.out.println("songs = " + servlet.getSongs().toString());
    }

    @Test
    public void getAllSongs() throws UnsupportedEncodingException {
        request.addParameter("all", "the best");
        request.addHeader("accept", JSON_CONTENT_TYPE);

        servlet.doGet(request, response);
        assert(!response.getContentAsString().isEmpty());
        assert(response.getStatus() == 200);
        System.out.println("do get all = " + response.getContentAsString());
    }

    @Test
    public void getWithId10() throws UnsupportedEncodingException {
        request.addParameter("songId", "10");
        request.addHeader("accept", JSON_CONTENT_TYPE);

        servlet.doGet(request, response);
        assert(!response.getContentAsString().isEmpty());
        assert(response.getStatus() == 200);
        assertEquals(songWithId10, response.getContentAsString().trim());

        System.out.println("do get <id=10> = " + response.getContentAsString());
    }

    @Test
    public void getNonExistentSong() throws UnsupportedEncodingException {
        request.addParameter("songId", "100");
        request.addHeader("accept", JSON_CONTENT_TYPE);

        servlet.doGet(request, response);
        assertEquals("Song with <id=100> not found!", response.getContentAsString().trim());
        assert(response.getStatus() == 404);
    }

    @Test
    public void getWithWrongHeader() throws UnsupportedEncodingException {
        request.addParameter("songId", "100");
        request.addHeader("accept", "me like i am");

        servlet.doGet(request, response);
        assert(response.getStatus() == 406);
        assertEquals("Could not interpret given header <me like i am>!", response.getContentAsString().trim());
    }

    @Test
    public void getWithWrongId() throws UnsupportedEncodingException {
        request.addParameter("songId", "nightclubbing");
        request.addHeader("accept", JSON_CONTENT_TYPE);

        servlet.doGet(request, response);
        assert(response.getStatus() == 400);
        assertEquals("Could not interpret given id!", response.getContentAsString().trim());
    }

    @Test
    public void postSong() {
        int counter = servlet.getCounter().get();
        request.setContentType(JSON_CONTENT_TYPE);
        request.setContent(Constants.songWithId10.getBytes());
        servlet.doPost(request, response);

        assert (response.getStatus() == 201);
        assertEquals(counter+1, servlet.getCounter().get());
    }

    @Test
    public void postNotJsonPayload() {
        request.setContentType("html");
        request.setContent(Constants.songWithId10.getBytes());
        servlet.doPost(request, response);
        assert (response.getStatus() == 400);
    }

    @Test
    public void postSongWithWrongJsonStructure() {
        String songWithWrongJsonStructure = "{\n" +
                "\"band\" : \"Metallica\",\n" +
                "\"instrument\" : \"Guitar\",\n" +
                "\"album\" : \"First Album\",\n" +
                "\"released\" : 2017\n" +
                "}";
        request.setContentType(JSON_CONTENT_TYPE);
        request.setContent(songWithWrongJsonStructure.getBytes());
        servlet.doPost(request, response);
        assert (response.getStatus() == 400);
    }
}
