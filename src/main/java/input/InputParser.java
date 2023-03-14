package input;

import exception.InputParsingException;
import utils.BitsOption;
import utils.Format;
import utils.IntOption;

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
    private String inputFile = "";
    private String outputFile = "";
    private String delimiter = "";

    private boolean shouldLookForFromOptions = false;
    private boolean shouldLookForToOptions = false;

    public ParserResult parse(String[] input) throws InputParsingException {
        if (input.length == 0) {
            throw new InputParsingException("Cannot run program without any arguments.");
        }

        var optionsFound = false;
        for (var argument : input) {
            if (shouldLookForFromOptions || shouldLookForToOptions) {
                optionsFound = parseOptions(argument);
                resetLookForOptionsFlags();
            }
            if (!optionsFound) {
                if (argument.startsWith("-") && !isAnyFlagEnabled()) {
                    parseFlag(argument);
                } else {
                    parseValue(argument);
                    resetFlags();
                }
            }
            optionsFound = false;
        }

        if (isAnyFlagEnabled() || fromRepresentation.equals("") || toRepresentation.equals("")) {
            throw new InputParsingException("Missing value for one of the switches.");
        }

        return new ParserResult(fromRepresentation, toRepresentation, fromOptions, toOptions, inputFile, outputFile,
                delimiter, helpFlag);
    }

    private void parseFlag(String argument) throws InputParsingException {
        if (argument.equals("-f") && fromRepresentation.isEmpty()) {
            fromFlag = true;
        } else if (argument.equals("-t") && toRepresentation.isEmpty()) {
            toFlag = true;
        } else if (argument.equals("-i") && inputFile.isEmpty()) {
            inputFileFlag = true;
        } else if (argument.equals("-o") && outputFile.isEmpty()) {
            outputFileFlag = true;
        } else if (argument.equals("-d") && delimiter.isEmpty()) {
            delimiterFlag = true;
        } else if (argument.equals("-h")) {
            helpFlag = true;
        } else {
            throw new InputParsingException("Invalid or duplicate switch encountered.");
        }
    }

    private void parseValue(String argument) throws InputParsingException {
        if (fromFlag && checkFormat(argument)) {
            fromRepresentation = argument;
            shouldLookForFromOptions = true;
        } else if (toFlag && checkFormat(argument)) {
            toRepresentation = argument;
            shouldLookForToOptions = true;
        } else if (inputFileFlag) {
            inputFile = argument;
        } else if (outputFileFlag) {
            outputFile = argument;
        } else if (delimiterFlag) {
            delimiter = argument;
        } else {
            throw new InputParsingException("Invalid value for one of the switches.");
        }
    }

    private boolean parseOptions(String argument) throws InputParsingException {
        if (shouldLookForFromOptions && argument.startsWith("--from-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkFromOption(argumentParts[1])) {
                fromOptions = argumentParts[1];
                return true;
            } else {
                throw new InputParsingException("Invalid option for this from format.");
            }
        } else if (shouldLookForToOptions && argument.startsWith("--to-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkToOption(argumentParts[1])) {
                toOptions = argumentParts[1];
                return true;
            } else {
                throw new InputParsingException("Invalid option for this to format");
            }
        }
        return false;
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
        return Format.contains(format);
    }

    private boolean checkFromOption(String option) {
        if (fromRepresentation.equals("int")) {
            return IntOption.contains(option);
        } else if (fromRepresentation.equals("bits")) {
            return BitsOption.contains(option);
        }
        return false;
    }

    private boolean checkToOption(String option) {
        if (toRepresentation.equals("int")) {
            return IntOption.contains(option);
        }
        return false;
    }

    private boolean isAnyFlagEnabled() {
        return fromFlag || toFlag || inputFileFlag || outputFileFlag || delimiterFlag;
    }
}
