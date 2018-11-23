package de.htw.ai.kbe.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class ServletTest {
    private Servlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() throws ServletException {
        servlet = new Servlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        config = new MockServletConfig();
        config.addInitParameter("jsonPath", "songs.json");
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initPath() {
        assert(!servlet.getJsonPath().isEmpty());
        System.out.println("jsonPath = " + servlet.getJsonPath());
    }

    @Test
    public void initSongs() {
        assert(!servlet.getSongs().isEmpty());
        System.out.println("songs = " + servlet.getSongs().toString());
    }

}
