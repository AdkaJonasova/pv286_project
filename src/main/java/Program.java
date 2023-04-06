import converters.ArrayConverter;
import exceptions.ConverterException;
import exceptions.InputParsingException;
import format.Format;
import input.InputParser;
import utils.IOHelper;
import utils.HelpPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static format.Format.ARRAY;

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

            // get input from user ot input file
            List<String> userInput;
            if (userArgs.getInputFile().isEmpty()) {
                userInput = IOHelper.readFromStandardInput(userArgs.getDelimiter());
            } else {
                userInput = IOHelper.readFromFile(userArgs.getInputFile(), userArgs.getDelimiter());
            }

            // convert values
            var result = new ArrayList<String>();
            for (var val : userInput) {
                Format from = userArgs.getFrom();
                Format to = userArgs.getTo();

                String convertedValue;

                if (ARRAY.equals(from) && ARRAY.equals(to)){
                    convertedValue = new ArrayConverter().convertFromArrayToArray(val, userArgs.getFromOptions());
                } else {
                    var convertedFromVal = from.getConverter().convertFrom(val, userArgs.getFromOptions());
                    convertedValue = to.getConverter().convertTo(convertedFromVal, userArgs.getToOptions());
                }
                
                result.add(convertedValue);
            }

            // write result in standard output or in the file
            if (userArgs.getOutputFile().isEmpty()) {
                IOHelper.writeToStandardOutput(result, userArgs.getDelimiter());
            } else {
                IOHelper.writeToFile(result, userArgs.getOutputFile(), userArgs.getDelimiter());
            }

        } catch (InputParsingException | ConverterException | IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}
