package input;

import exception.InputParsingException;
import options.BitsOption;
import format.Format;
import options.IOption;
import options.IntOption;
import utils.Flag;

import java.util.Objects;

public class InputParser {

    private Flag currentFlag = null;

    private String fromRepresentation = "";
    private String toRepresentation = "";
    private IOption fromOptions = null;
    private IOption toOptions = null;
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
                if (Objects.isNull(currentFlag) && (argument.startsWith("-") || argument.startsWith("--"))) {
                    parseFlag(argument);
                } else if (Objects.nonNull(currentFlag)) {
                    parseValue(argument);
                    currentFlag = null;
                } else {
                    throw new InputParsingException(String.format("Argument %s not allowed here", argument));
                }
            }
            optionsFound = false;
        }

        var shouldPrintHelp = Objects.nonNull(currentFlag) && currentFlag.equals(Flag.HELP);
        if (!shouldPrintHelp && (Objects.nonNull(currentFlag) || fromRepresentation.equals("") || toRepresentation.equals(""))) {
            throw new InputParsingException("Missing value for one of the switches.");
        }

        return new ParserResult(Format.fromString(fromRepresentation), Format.fromString(toRepresentation), fromOptions,
                toOptions, inputFile, outputFile, delimiter, shouldPrintHelp);
    }

    private void parseFlag(String argument) throws InputParsingException {
        // try to resolve short version of flag
        currentFlag = Flag.getByShort(argument);
        if (Objects.nonNull(currentFlag)) {
            return;
        }

        // long versions of flags are in format: --flag=value
        var splitArgument= argument.split("=");
        if (splitArgument.length != 2) {
            throw new InputParsingException(String.format("Invalid argument encountered -> %s", argument));
        }

        // try to resolve long version of flag
        currentFlag = Flag.getByLong(splitArgument[0]);
        if (Objects.isNull(currentFlag)) {
            throw new InputParsingException(String.format("Invalid flag encountered -> %s", splitArgument[0]));
        }
        // parse value part from long version of flag
        parseValue(splitArgument[1]);
    }

    private void parseValue(String argument) throws InputParsingException {
        if (currentFlag.equals(Flag.FROM) && fromRepresentation.isEmpty() && checkFormat(argument)) {
            fromRepresentation = argument;
            shouldLookForFromOptions = true;
        } else if (currentFlag.equals(Flag.TO) && toRepresentation.isEmpty() && checkFormat(argument)) {
            toRepresentation = argument;
            shouldLookForToOptions = true;
        } else if (currentFlag.equals(Flag.INPUT_FILE)) {
            inputFile = argument;
        } else if (currentFlag.equals(Flag.OUTPUT_FILE)) {
            outputFile = argument;
        } else if (currentFlag.equals(Flag.DELIMITER)) {
            delimiter = argument;
        } else {
            throw new InputParsingException("Invalid value for one of the switches.");
        }
    }

    private boolean parseOptions(String argument) throws InputParsingException {
        if (shouldLookForFromOptions && argument.startsWith("--from-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && resolveFromOptions(argumentParts[1])) {
                return true;
            } else {
                throw new InputParsingException("Invalid option for this from format.");
            }
        } else if (shouldLookForToOptions && argument.startsWith("--to-options")) {
            var argumentParts = argument.split("=");
            if (argumentParts.length == 2 && resolveToOptions(argumentParts[1])) {;
                return true;
            } else {
                throw new InputParsingException("Invalid option for this to format");
            }
        }
        return false;
    }

    private void resetLookForOptionsFlags() {
        shouldLookForFromOptions = false;
        shouldLookForToOptions = false;
    }

    private boolean checkFormat(String format) {
        return Format.contains(format);
    }

    private boolean resolveFromOptions(String option) {
        if (fromRepresentation.equals("int") && IntOption.contains(option)) {
            fromOptions = IntOption.fromString(option);
            return true;
        } else if (fromRepresentation.equals("bits") && BitsOption.contains(option)) {
            fromOptions = BitsOption.fromString(option);
            return true;
        }
        return false;
    }

    private boolean resolveToOptions(String option) {
        if (toRepresentation.equals("int")) {
            toOptions = IntOption.fromString(option);
            return true;
        }
        return false;
    }
}
