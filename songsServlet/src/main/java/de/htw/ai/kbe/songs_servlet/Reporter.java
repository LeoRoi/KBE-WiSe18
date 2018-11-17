package de.htw.ai.kbe.songs_servlet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporter {
    private List<String> methodsWithAnno = new ArrayList<String>();
    private List<String> methodsWithoutAnno = new ArrayList<String>();
    private Map<String, String> methodsWithAnnoAndError = new HashMap<>();

    public List<String> getMethodsWithAnno() {
        return methodsWithAnno;
    }

    public List<String> getMethodsWithoutAnno() {
        return methodsWithoutAnno;
    }

    public Map<String, String> getMethodsWithAnnoAndError() {
        return methodsWithAnnoAndError;
    }

    public void print(String fileName) {

        // Defines default file name as report.txt
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "report.txt";
        }

        try {
            PrintWriter writer = new PrintWriter("reports" + fileName, "UTF-8");

            if (methodsWithoutAnno.isEmpty()) {
                writer.println("No Methods without RunMe!");
            } else {
                writer.println("Methods without RunMe: " + methodsWithoutAnno);
            }

            if (methodsWithAnno.isEmpty()) {
                writer.println("No Methods with RunMe!");
            } else {
                writer.println("Methods with RunMe: " + methodsWithAnno);
            }

            if (methodsWithAnnoAndError.isEmpty()) {
                writer.println("No Methods with RunMe and Errors!");
            } else {
                writer.println("Invokable Methods with RunMe: " + methodsWithAnnoAndError);
            }

            writer.close();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (FileNotFoundException nfe) {
            System.out.println("404");
        }


    }
}
