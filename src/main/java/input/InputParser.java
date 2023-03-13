package input;

import exception.InputParsingException;

import java.util.List;

public class InputParser {

    private boolean fromFlag = false;
    private boolean toFlag = false;
    private boolean inputFileFlag = false;
    private boolean outputFileFlag = false;
    private boolean delimiterFlag = false;
    private boolean helpFlag = false;

    private String fromRepresentation = "";
    private String toRepresentation = "";
    private String fromOptions = "";
    private String toOptions = "";
    private String delimiter = "";

    private boolean shouldLookForFromOptions = false;
    private boolean shouldLookForToOptions = false;

    public ParserResult parse(String[] input) throws InputParsingException {
        for (var argument : input) {
            if (shouldLookForFromOptions || shouldLookForToOptions) {
                parseOptions(argument);
                resetLookForOptionsFlags();
            } else if (argument.startsWith("-")) {
                parseFlag(argument);
            } else {
                parseValue(argument);
                resetFlags();
            }
        }
        return new ParserResult(fromRepresentation, toRepresentation, fromOptions, toOptions);
    }

    private void parseFlag(String argument) {
        if (argument.equals("-f")) {
            fromFlag = true;
        } else if (argument.equals("-t")) {
            toFlag = true;
        } else if (argument.equals("-i")) {
            inputFileFlag = true;
        } else if (argument.equals("-o")) {
            outputFileFlag = true;
        } else if (argument.equals("-d")) {
            delimiterFlag = true;
        } else if (argument.equals("-h")) {
            helpFlag = true;
        } else {
            // chyba
        }
    }

    private void parseValue(String argument) {
        if (fromFlag && checkFormat(argument)) {
            fromRepresentation = argument;
            shouldLookForFromOptions = true;
        } else if (toFlag && checkFormat(argument)) {
            toRepresentation = argument;
            shouldLookForToOptions = true;
        } else if (delimiterFlag) {
            delimiter = argument;
        } else {
            // chyba
        }
    }

    private void parseOptions(String argument) throws InputParsingException {
        if (shouldLookForFromOptions && argument.startsWith("--from-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkFromOption(argumentParts[1])) {
                fromOptions = argumentParts[1];
            } else {
                throw new InputParsingException();
            }
        } else if (shouldLookForToOptions && argument.startsWith("--to-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkToOption(argumentParts[1])) {
                toOptions = argumentParts[1];
            } else {
                throw new InputParsingException();
            }
        }
    }

    private void resetFlags() {
        fromFlag = false;
        toFlag = false;
        inputFileFlag = false;
        outputFileFlag = false;
        delimiterFlag = false;
        helpFlag = false;
    }

    private void resetLookForOptionsFlags() {
        shouldLookForFromOptions = false;
        shouldLookForToOptions = false;
    }

    private boolean checkFormat(String format) {
        List<String> possibleFormats = List.of("bytes", "hex", "int", "bits", "array");
        return possibleFormats.contains(format);
    }

    private boolean checkFromOption(String option) {
        List<String> possibleIntOptions = List.of("big", "little");
        List<String> possibleBitOptions = List.of("left", "right");

        if (fromRepresentation.equals("int")) {
            return possibleIntOptions.contains(option);
        } else if (fromRepresentation.equals("bits")) {
            return possibleBitOptions.contains(option);
        }
        return false;
    }

    private boolean checkToOption(String option) {
        List<String> possibleIntOptions = List.of("big", "little");

        if (fromRepresentation.equals("int")) {
            return possibleIntOptions.contains(option);
        }
        return false;
    }
}
