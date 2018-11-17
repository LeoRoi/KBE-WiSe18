package de.htw.ai.kbe.songs_servlet;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.Assert.assertTrue;

public class ReporterTest {

    @Test
    public void givenTestClassWhenPrintedThenReturnCompleteReport() throws IOException {

        String[] argsComplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", "-c className", "-o report.txt"};
        Analyser analyser = new Analyser(argsComplete);
        Class RunMeClass = RunMe.class;
        String[] parsedInput = analyser.parseCL();
        String classToAnalyse = parsedInput[0];
        String outputFile = parsedInput[1];
        //String packagePath = "de.htw.ai.kbe.runmerunner";
        Reporter report = analyser.analyse("de.htw.ai.kbe.runmerunner.UseMe", RunMeClass);
        report.print(outputFile);

        File reportExpected =
                new File("src\\test\\java\\de\\htw\\ai\\kbe\\runmerunner\\testResources\\reportExpected.txt");
        File reportActual = new File( "reports\\report.txt");
        assertTrue("The files differ", FileUtils.contentEquals(reportExpected, reportActual));
    }

    @After
    public void cleanUp() {
        Path path = FileSystems.getDefault().getPath("", "reports report.txt");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

}