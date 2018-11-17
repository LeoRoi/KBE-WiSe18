package de.htw.ai.kbe.songs_servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) {
        Analyser analyser = new Analyser(args);
        String[] parsedInput = analyser.parseCL();
        String classToAnalyse = parsedInput[0];
        String outputFile = parsedInput[1];

        String packagePath = "de.htw.ai.kbe.runmerunner";
        Class RunMeClass = RunMe.class;
        Reporter report = analyser.analyse(packagePath + "." + classToAnalyse, RunMeClass);
        report.print(outputFile);
    }
}
