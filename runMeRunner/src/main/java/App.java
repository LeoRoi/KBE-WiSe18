import org.apache.commons.cli.*;

public class App {
    public static void main(String[] args) {
//        for (String temp : args) {
//            System.out.println(temp);
//        }
//
//        System.out.println("input class" + args[0]);

        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        Option inputClass = new Option("c", "input", true, "Der Name einer Klasse");
        inputClass.setRequired(true);
        inputClass.setArgName("Klassenname");
        options.addOption(inputClass);

        Option outputFile = new Option("o", "output", true, "Der Name eines Ausgabefiles");
        inputClass.setArgName("Ausgabefile");
        options.addOption(outputFile);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            String in = line.getOptionValue("c");
            String out = line.getOptionValue("o");

            System.out.println("Input class: " + in);
//            if(out != null && !out.isEmpty()) {
            if(out != null) {
                System.out.println("Report: " + out);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            formatter.printHelp("Parameters", options);
        }
    }
}
