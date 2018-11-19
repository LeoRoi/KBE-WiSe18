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

    private final static String URITODB_STRING = "emotional wasteland";

    @Before
    public void setUp() throws ServletException {
        servlet = new Servlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();

        config.addInitParameter("uriToDB", URITODB_STRING);
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initShouldSetMySignature() {
        assertEquals(URITODB_STRING, servlet.uriToDB);
    }


}
