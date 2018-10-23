import org.apache.commons.cli.*;

public class App {
    public static void main(String[] args) {
//        for (String temp : args) {
//            System.out.println(temp);
//        }
//
//        System.out.println("input class" + args[0]);
//        System.out.println("input class" + args[0]);

        Options options = new Options();
        options.addOption("c", "Class", true, "Der Name einer Klasse");
        options.addOption("o", "Output", true, "Der Name eines Ausgabefiles");

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine line = parser.parse(options, args);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
}
