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
    void sendResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(JSON_CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        response.getWriter().println(message);
    }

    boolean isInteger(String string) {
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

    boolean requestAcceptHeaderOk(String acceptHeader) {
        return (stringOk(acceptHeader) && ((JSON_CONTENT_TYPE.equals(acceptHeader) || "*".equals(acceptHeader))));
    }

    /**
     * @param str acceptHeader
     * @return true if not null and not empty
     */
    boolean stringOk(final String str) {
        return (str != null && !str.trim().isEmpty());
    }

    //TODO why not simply request.getParameterMap()?
    Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> acc = new HashMap<>();
        Enumeration<String> paramsEnum = request.getParameterNames();

        while (paramsEnum.hasMoreElements()) {
            String parameter = paramsEnum.nextElement();
            acc.put(parameter, request.getParameter(parameter));
        }

        return acc;
    }

    // from jaxbjackson
    List<Song> readJSONToSongs(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }

    /*// Write a List<Song> into a JSON-file. from jaxbjackson
    void writeSongsToJSON(List<Song> songs, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
            objectMapper.writeValue(os, songs);
        }
    }*/

    boolean jsonStructureOk(Map jsonMap) {
        boolean keysOk = jsonMap.containsKey("title") && jsonMap.containsKey("artist") && jsonMap.containsKey("album")
                && jsonMap.containsKey("released");
        boolean valuesOk = jsonMap.get("released") instanceof Integer;
        return keysOk && valuesOk;
    }


}

