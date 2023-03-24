import exception.InputParsingException;
import input.InputParser;
import utils.FileHelper;
import utils.HelpPrinter;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            var userArgs = inputParser.parse(args);

            // if program should print help, don't ask for input
            if (userArgs.getShouldPrintHelp()) {
                HelpPrinter.printHelp();
                return;
            }

            var userInput = FileHelper.readFromFile(userArgs.getInputFile(), userArgs.getDelimiter());
            var result = new ArrayList<String>();
            for (var val : userInput) {
                var convertedFromVal = userArgs.getFrom().getConverter().convertFrom(val, userArgs.getFromOptions());
                var convertedValue = userArgs.getTo().getConverter().convertTo(convertedFromVal, userArgs.getToOptions());
                result.add(convertedValue);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter value you want to convert: ");
            var valueToConvert = scanner.nextLine();

            var convertedFromVal = userArgs.getFrom().getConverter().convertFrom(valueToConvert, userArgs.getFromOptions());
            var convertedValue = userArgs.getTo().getConverter().convertTo(convertedFromVal, userArgs.getToOptions());
            System.out.println(convertedValue);

        } catch (InputParsingException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}
