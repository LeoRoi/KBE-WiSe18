package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.htw.ai.kbe.servlet.Constants.*;

class Utils {
    boolean requestAcceptHeaderOk(String acceptHeader) {
        return (stringOk(acceptHeader) || (APPLICATION_JSON.equals(acceptHeader) || "*".equals(acceptHeader)));
    }

    boolean stringOk(final String str) {
        return (str != null && !str.trim().isEmpty());
    }

    Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> acc = new HashMap<>();
        Enumeration<String> paramsEnum = request.getParameterNames();

        while(paramsEnum.hasMoreElements()) {
            String parameter = paramsEnum.nextElement();
            acc.put(parameter, request.getParameter(parameter));
        }

        return acc;
    }

    // from jaxbjackson
    List<Song> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }
}
