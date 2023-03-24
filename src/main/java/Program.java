import exception.InputParsingException;
import input.InputParser;
import utils.HelpPrinter;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            var result = inputParser.parse(args);

            // if program should print help, don't ask for input
            if (result.getShouldPrintHelp()) {
                HelpPrinter.printHelp();
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter value you want to convert: ");
            var valueToConvert = scanner.nextLine();

            var convertedFromVal = result.getFrom().getConverter().convertFrom(valueToConvert, result.getFromOptions());
            var convertedValue = result.getTo().getConverter().convertTo(convertedFromVal, result.getToOptions());
            System.out.println(convertedValue);

        } catch (InputParsingException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}
