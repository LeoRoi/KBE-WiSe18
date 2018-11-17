package de.htw.ai.kbe.songs_servlet;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class AnalyserTest {

    private Reporter reporterExpectedForSubmitTestClass;
    private Reporter reporterExpectedForClassMitRM;
    private Reporter reporterActualForSubmitTestClass;
    private Reporter reporterActualForClassMitRM;
    private String[] argsComplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", "-c className", "-o repo.txt"};
    private Analyser analyser = new Analyser(argsComplete);
    private Class RunMeClass = RunMe.class;

    @Before
    public void methodBefore() {
        reporterExpectedForSubmitTestClass = new Reporter();
        List<String> methodsWithAnno = Arrays.asList("method0", "method1", "method2", "method3", "method4", "toString");
        reporterExpectedForSubmitTestClass.getMethodsWithAnno().addAll(methodsWithAnno);

        List<String> methodsWithoutAnno = Arrays.asList("noRmR1", "noRmR2", "noRmR3");
        reporterExpectedForSubmitTestClass.getMethodsWithoutAnno().addAll(methodsWithoutAnno);
        reporterExpectedForSubmitTestClass.getMethodsWithAnnoAndError().put("method4", "IllegalArgumentException");

        reporterActualForSubmitTestClass =
                analyser.analyse("de.htw.ai.kbe.runmerunner.testResources.AbgabeTestClass", RunMeClass);


        reporterExpectedForClassMitRM = new Reporter();
        reporterExpectedForClassMitRM.getMethodsWithAnno().add("protectedMethodWithRunMe");
        List<String> methodsWithAnno2 =
                Arrays.asList(/*"methodWithRandomAnnotation", */"methodWithoutAnnotation", "protectedMethodWithoutRunMe");
        reporterExpectedForClassMitRM.getMethodsWithoutAnno().addAll(methodsWithAnno2);
        reporterExpectedForClassMitRM.getMethodsWithAnno().add("methodWithAnnotationRunMe");
        reporterExpectedForClassMitRM.getMethodsWithAnnoAndError().put("methodWithParameter", "IllegalArgumentException");

        reporterActualForClassMitRM =
                analyser.analyse("de.htw.ai.kbe.runmerunner.testResources.ClassMitRM", RunMeClass);

    }

    @Test
    public void givenCompleteArgsWhenParsedThenSuccessful() {
        String[] expected = {" className", " repo.txt"};
        String[] actual = analyser.parseCL();
        assertArrayEquals("failure - Arrays are not the same", expected , actual);
    }


    @Test
    public void givenIncompleteArgsWhenParsedThenReturnIncomplete() {
        String[] argsIncomplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", "-c className", "-o"};
        analyser = new Analyser(argsIncomplete);
        String[] expected = {"", ""};
        String[] actual = analyser.parseCL();
        assertArrayEquals("failure - Arrays are not the same", expected , actual);
    }


    @Test
    public void givenNoArgsWhenParsedThenReturnEmpty() {
        String[] argsIncomplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar"};
        analyser = new Analyser(argsIncomplete);
        String[] expected = {"", ""};
        String[] actual = analyser.parseCL();
        assertArrayEquals("failure - Arrays are not the same", expected , actual);
    }

    @Test
    public void givenMisspelledOptionWhenParsedThenReturnEmpty() {
        String[] argsIncomplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", ".c -o rapport.txt"};
        analyser = new Analyser(argsIncomplete);
        String[] expected = {"", ""};
        String[] actual = analyser.parseCL();
        assertArrayEquals("failure - Arrays are not the same", expected , actual);
    }

    @Test
    public void givenMisspelledArgWhenParsedThenReturnEmpty() {
        String[] argsIncomplete = {"java -jar runmerunner-1.0-jar-with-dependencies.jar", ".c -o rapport.tt"};
        analyser = new Analyser(argsIncomplete);
        String[] expected = {"", ""};
        String[] actual = analyser.parseCL();
        assertArrayEquals("failure - Arrays are not the same", expected , actual);
    }


    @Test
    public void givenTestClassWhenAnalysedThenReturnCompleteList1() {
        List<String> methodsWithAnnoExpected = reporterExpectedForClassMitRM.getMethodsWithAnno();
        List<String> methodsWithAnnoActual = reporterActualForClassMitRM.getMethodsWithAnno();

        assertTrue(methodsWithAnnoActual.containsAll(methodsWithAnnoExpected));
        assertTrue(methodsWithAnnoActual.removeAll(methodsWithAnnoExpected));
    }

    @Test
    public void givenTestClassWhenAnalysedThenReturnCompleteList2() {
        List<String> methodsWithoutAnnoExpected = reporterExpectedForClassMitRM.getMethodsWithoutAnno();
        List<String> methodsWithoutAnnoActual = reporterActualForClassMitRM.getMethodsWithoutAnno();

        assertTrue(methodsWithoutAnnoActual.containsAll(methodsWithoutAnnoExpected));
    }

    @Test
    public void givenTestClassWhenAnalysedThenReturnCompleteMap() {
        Map<String, String> methodsWithAnnoAndErrorExpected = reporterExpectedForClassMitRM.getMethodsWithAnnoAndError();
        Map<String, String> methodsWithAnnoAndErrorActual = reporterActualForClassMitRM.getMethodsWithAnnoAndError();

        assertTrue(methodsWithAnnoAndErrorActual.keySet().containsAll(methodsWithAnnoAndErrorExpected.keySet()));
    }


    @Test
    public void givenSubmitTestClassWhenAnalysedThenReturnCompleteList1() {
        List<String> methodsWithAnnoExpected = reporterExpectedForSubmitTestClass.getMethodsWithAnno();
        List<String> methodsWithAnnoActual = reporterActualForSubmitTestClass.getMethodsWithAnno();

        assertTrue(methodsWithAnnoActual.containsAll(methodsWithAnnoExpected));
    }

    @Test
    public void givenSubmitTestClassWhenAnalysedThenReturnCompleteList2() {
        List<String> methodsWithoutAnnoExpected = reporterExpectedForSubmitTestClass.getMethodsWithoutAnno();
        List<String> methodsWithoutAnnoActual = reporterActualForSubmitTestClass.getMethodsWithoutAnno();

        assertTrue(methodsWithoutAnnoActual.containsAll(methodsWithoutAnnoExpected));
    }

    @Test
    public void givenSubmitTestClassWhenAnalysedThenReturnCompleteMap() {
        Map<String, String> methodsWithAnnoAndErrorExpected =
                reporterExpectedForSubmitTestClass.getMethodsWithAnnoAndError();

        Map<String, String> methodsWithAnnoAndErrorActual =
                reporterActualForSubmitTestClass.getMethodsWithAnnoAndError();

        assertTrue(methodsWithAnnoAndErrorActual.keySet().containsAll(methodsWithAnnoAndErrorExpected.keySet()));
    }


    @Test
    public void givenWrongPathWhenAnalysedThenReturnEmptyReportObject() {
        Reporter reporterExpected = new Reporter();
        Reporter reporterActual = analyser.analyse("wrongPath", RunMeClass);
        assertThat(reporterExpected, samePropertyValuesAs(reporterActual));
    }

    @Test
    public void givenInterfaceWhenAnalysedThenReturnCompleteReporterObject() {
        Reporter reporterExpected = new Reporter();
        reporterExpected.getMethodsWithAnnoAndError().put("methodWithAnnotation", "InstantiationException");
        reporterExpected.getMethodsWithoutAnno().add("methodWithoutAnnotation");

        Reporter reporterActual =
                analyser.analyse("de.htw.ai.kbe.runmerunner.testResources.InterfaceMitRM", RunMeClass);

        assertThat(reporterExpected, samePropertyValuesAs(reporterActual));
    }


    @Test
    public void givenAbstractClassWhenAnalysedThenReturnCompleteReporterObject() {
        Reporter reporterExpected = new Reporter();
        reporterExpected.getMethodsWithAnnoAndError().put("abstractMethodWithAnnotation", "InstantiationException");
        reporterExpected.getMethodsWithoutAnno().add("abstractMethodWithoutAnnotation");

        Reporter reporterActual =
                analyser.analyse("de.htw.ai.kbe.runmerunner.testResources.AbstractClassMitRM", RunMeClass);

        assertThat(reporterExpected, samePropertyValuesAs(reporterActual));
    }

}
