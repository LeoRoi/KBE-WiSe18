package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Analyser {
    private String[] args;

    Analyser(String[] args) {
        this.args = args;
    }

    String[] parseCL() {
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();
        String givenClass = "";
        String reportFile = "";

        String cn = "class name";
        Option inputClass = new Option("c", "input", true, cn);
        inputClass.setRequired(true);
        inputClass.setArgName(cn);
        options.addOption(inputClass);

        String fn = "output file name";
        Option outputFile = new Option("o", "output", true, fn);
        outputFile.setArgName(fn);
        options.addOption(outputFile);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            givenClass = line.getOptionValue("c");
            reportFile = line.getOptionValue("o");
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            formatter.printHelp("Parameters", options);
        }
        return new String[] {givenClass, reportFile};
    }

    Reporter analyse(String analyseMe, String annotationWeNeed) {
        Reporter r = new Reporter();

        try {
            Class<?> clazz = Class.forName(analyseMe);
            r = getMethodNames(clazz, clazz.getDeclaredMethods(), annotationWeNeed);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    }

    private Reporter getMethodNames(Class<?> clazz, Method[] methods, String annotationWeNeed) {
        Reporter r = new Reporter();

        for (Method m : methods) {
            Annotation[] annotations = m.getDeclaredAnnotations();

            if (annotations.length > 0) {
                for (Annotation a : annotations) {
                    if (a.annotationType().getName().equals(annotationWeNeed)) {
                        try {
                            Object obj = clazz.newInstance();
                            m.invoke(obj);
                            r.getMethodsWithAnno().add(m.getName());
                        } catch (Exception e) {
                            r.getMethodsWithAnnoAndError().put(m.getName(), e.getClass().getSimpleName());
                        }
                    }
                }
            } else {
                r.getMethodsWithoutAnno().add(m.getName());
            }
        }
        return r;
    }
}

