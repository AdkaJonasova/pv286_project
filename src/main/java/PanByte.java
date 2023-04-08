import converters.ArrayConverter;
import exceptions.ConverterException;
import exceptions.InputParsingException;
import format.Format;
import input.IArgumentParser;
import input.ArgumentParser;
import input.ParserResult;
import utils.IOHelper;
import utils.HelpPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static format.Format.ARRAY;

public class PanByte {

    public static void main(String[] args) {
        run(args);
    }

    private static void run(String[] args) {
        IArgumentParser inputParser = new ArgumentParser();
        try {
            var userArgs = inputParser.parse(args);

            // if program should print help, don't ask for input
            if (userArgs.getShouldPrintHelp()) {
                HelpPrinter.printHelp();
                return;
            }

            List<String> userInput = getUserInput(userArgs);
            ArrayList<String> result = convertInputWithArgs(userInput, userArgs);
            writeOutput(userArgs, result);

        } catch (InputParsingException | ConverterException | IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void writeOutput(ParserResult userArgs, ArrayList<String> result) throws IOException {
        if (userArgs.getOutputFile().isEmpty()) {
            IOHelper.writeToStandardOutput(result, userArgs.getDelimiter());
        } else {
            IOHelper.writeToFile(result, userArgs.getOutputFile(), userArgs.getDelimiter());
        }
    }

    private static ArrayList<String> convertInputWithArgs(List<String> userInput, ParserResult userArgs) throws ConverterException {
        var result = new ArrayList<String>();
        for (var val : userInput) {
            Format from = userArgs.getFrom();
            Format to = userArgs.getTo();

            String convertedValue;

            if (ARRAY.equals(from) && ARRAY.equals(to)) {
                convertedValue = new ArrayConverter().convertFromArrayToArray(val, userArgs.getToOptions());
            } else {
                var convertedFromVal = from.getConverter().convertFrom(val, userArgs.getFromOptions());
                convertedValue = to.getConverter().convertTo(convertedFromVal, userArgs.getToOptions());
            }

            result.add(convertedValue);
        }
        return result;
    }

    private static List<String> getUserInput(ParserResult userArgs) throws IOException {
        List<String> userInput;
        if (userArgs.getInputFile().isEmpty()) {
            userInput = IOHelper.readFromStandardInput(userArgs.getDelimiter());
        } else {
            userInput = IOHelper.readFromFile(userArgs.getInputFile(), userArgs.getDelimiter());
        }
        return userInput;
    }
}
