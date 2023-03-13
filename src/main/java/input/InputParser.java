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
    private String delimiter = "";

    private boolean shouldLookForFromOptions = false;
    private boolean shouldLookForToOptions = false;

    public ParserResult parse(String[] input) throws InputParsingException {
        if (input.length == 0) {
            throw new InputParsingException();
        }

        var optionsFound = false;
        for (var argument : input) {
            if (shouldLookForFromOptions || shouldLookForToOptions) {
                optionsFound = parseOptions(argument);
                resetLookForOptionsFlags();
            }
            if (!optionsFound) {
                if (argument.startsWith("-")) {
                    parseFlag(argument);
                } else {
                    parseValue(argument);
                    resetFlags();
                }
            }
            optionsFound = false;
        }
        return new ParserResult(fromRepresentation, toRepresentation, fromOptions, toOptions, helpFlag);
    }

    private void parseFlag(String argument) throws InputParsingException {
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
            throw new InputParsingException();
        }
    }

    private void parseValue(String argument) throws InputParsingException {
        if (fromFlag && checkFormat(argument)) {
            fromRepresentation = argument;
            shouldLookForFromOptions = true;
        } else if (toFlag && checkFormat(argument)) {
            toRepresentation = argument;
            shouldLookForToOptions = true;
        } else if (delimiterFlag) {
            delimiter = argument;
        } else {
            throw new InputParsingException();
        }
    }

    private boolean parseOptions(String argument) throws InputParsingException {
        if (shouldLookForFromOptions && argument.startsWith("--from-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkFromOption(argumentParts[1])) {
                fromOptions = argumentParts[1];
                return true;
            } else {
                throw new InputParsingException();
            }
        } else if (shouldLookForToOptions && argument.startsWith("--to-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && checkToOption(argumentParts[1])) {
                toOptions = argumentParts[1];
                return true;
            } else {
                throw new InputParsingException();
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
}
