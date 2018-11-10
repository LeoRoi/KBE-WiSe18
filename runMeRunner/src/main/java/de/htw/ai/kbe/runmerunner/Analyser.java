package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Analyser {
    private String[] args;

    Analyser(String[] args) {
        this.args = args;
    }

    /**
     * Parses the command line input and extracts the java class name and the name of the report txt file.
     * Uses the 'common cli' library to do so.
     * @return The java class name and the txt file name for the report
     */
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

    /**
     *
     * @param analyseMe The file path to the java file, which will be analysed. The analyses regards the methods which:
     *                  1. have the given annotation (next parameter)
     *                  2. have no annotation
     *                  3. can't be invoked.
     * @param annotationWeNeed The file path to the annotation that will be used to analyse the methods of the
     *                         java file.
     * @return An object 'Reporter' which contains lists of all methods with the given annotation, all methods without
     *         the given annotation and methods which cannot be invoked.
     */
    Reporter analyse(String analyseMe, Class annotationWeNeed) {
        Reporter r = new Reporter();

        try {
            Class<?> clazz = Class.forName(analyseMe);
            r = getMethodNames(clazz, clazz.getDeclaredMethods(), annotationWeNeed.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     *
     * @param clazz Java class that is analysed.
     * @param methods All declared methods of the java file.
     * @param annotationWeNeed All methods will be checked whether they have the given annotation.
     * @return An object 'Reporter' which contains lists of all methods with the given annotation, all methods without
     * the given annotation and methods which cannot be invoked.
     */
    private Reporter getMethodNames(Class<?> clazz, Method[] methods, String annotationWeNeed) {
        Reporter r = new Reporter();

        for (Method m : methods) {
            Annotation[] annotations = m.getDeclaredAnnotations();

            if (annotations.length > 0) {
                for (Annotation a : annotations) {
                    if (a.annotationType().getName().equals(annotationWeNeed)) {
                        try {
                            Object obj = clazz.newInstance();
                            m.setAccessible(true);
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

