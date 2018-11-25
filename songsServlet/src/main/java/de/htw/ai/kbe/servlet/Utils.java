package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.htw.ai.kbe.servlet.Constants.*;

class Utils {
    private Utils() {
    }
    // private constructor to avoid unnecessary instantiation of the class

    static void sendResponse(HttpServletResponse response, int status, String message) {
        try {
            response.setStatus(status);
            response.setContentType(JSON_CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    static boolean requestAcceptHeaderOk(String acceptHeader) {
        return (stringOk(acceptHeader) && ((JSON_CONTENT_TYPE.equals(acceptHeader) || "*".equals(acceptHeader))));
    }

    static boolean stringOk(final String str) {
        return (str != null && !str.trim().isEmpty());
    }

    static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> acc = new HashMap<>();
        Enumeration<String> paramsEnum = request.getParameterNames();

        while (paramsEnum.hasMoreElements()) {
            String parameter = paramsEnum.nextElement();
            acc.put(parameter, request.getParameter(parameter));
        }

        return acc;
    }

    // from jaxbjackson
    static List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }

    // Write a List<Song> into a JSON-file. from jaxbjackson
    static void writeSongsToJSON(List<Song> songs, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
            objectMapper.writeValue(os, songs);
        }
    }
}
