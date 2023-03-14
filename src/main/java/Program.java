import exception.InputParsingException;
import input.InputParser;
import utils.HelpPrinter;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            HelpPrinter.printHelp();
            var result = inputParser.parse(args);
            System.out.println(result.toString());
        } catch (InputParsingException e) {
            System.out.println("Chyba");
        }
    }
}
