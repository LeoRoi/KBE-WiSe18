package de.htw.ai.kbe.runmerunner;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ReporterTest {

    @Test
    public void givenTestClassWhenPrintedThenReturnCompleteReport() throws IOException {

        String[] argsComplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", "-c className", "-o repo.txt"};
        Analyser analyser = new Analyser(argsComplete);
        Class RunMeClass = RunMe.class;
        String[] parsedInput = analyser.parseCL();
        String classToAnalyse = parsedInput[0];
        String outputFile = parsedInput[1];
        String packagePath = "de.htw.ai.kbe.runmerunner";
        Reporter report = analyser.analyse("de.htw.ai.kbe.runmerunner.testResources.AbgabeTestClass", RunMeClass);
        report.print(outputFile);

        File reportExpected = new File("de.htw.ai.kbe.runmerunner.testResources.reportExpected.txt");
        File reportActual = new File("..//..//..//..//repo.txt");
        assertTrue("The files differ", FileUtils.contentEquals(reportExpected, reportActual));
    }

}