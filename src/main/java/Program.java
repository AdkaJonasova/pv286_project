import exceptions.ConverterException;
import exceptions.InputParsingException;
import input.InputParser;
import utils.IOHelper;
import utils.HelpPrinter;

import java.util.ArrayList;
import java.util.List;

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

            List<String> userInput;
            if (userArgs.getInputFile().isEmpty()) {
                userInput = IOHelper.readFromStandardInput(userArgs.getDelimiter());
            } else {
                userInput = IOHelper.readFromFile(userArgs.getInputFile(), userArgs.getDelimiter());
            }

            var result = new ArrayList<String>();
            for (var val : userInput) {
                var convertedFromVal = userArgs.getFrom().getConverter().convertFrom(val, userArgs.getFromOptions());
                var convertedValue = userArgs.getTo().getConverter().convertTo(convertedFromVal, userArgs.getToOptions());
                result.add(convertedValue);
            }

            if (userArgs.getOutputFile().isEmpty()) {
                IOHelper.writeToStandardOutput(result, userArgs.getDelimiter());
            } else {
                IOHelper.writeToFile(result, userArgs.getOutputFile(), userArgs.getDelimiter());
            }

        } catch (InputParsingException | ConverterException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}
