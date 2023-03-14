import exception.InputParsingException;
import input.InputParser;
import utils.HelpPrinter;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            var result = inputParser.parse(args);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter value you want to convert: ");
            var valueToConvert = scanner.nextLine();

            if (result.getShouldPrintHelp()) {
                HelpPrinter.printHelp();
            }

            var convertedFromVal = result.getFrom().getConverter().convertFrom(valueToConvert, result.getFromOption());
            var convertedValue = result.getTo().getConverter().convertTo(convertedFromVal, result.getToOption());
            System.out.println(convertedValue);
        } catch (InputParsingException e) {
            System.out.println("Chyba");
        }
    }
}
