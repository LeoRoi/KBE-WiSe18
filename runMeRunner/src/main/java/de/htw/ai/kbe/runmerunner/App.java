package de.htw.ai.kbe.runmerunner;

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
