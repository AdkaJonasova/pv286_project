package input;

import exceptions.InputParsingException;
import options.BitsOption;
import format.Format;
import options.IOption;
import options.IntOption;
import utils.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputParser {

    private Flag currentFlag;

    private Format fromRepresentation;
    private Format toRepresentation;
    private List<IOption> fromOptions;
    private List<IOption> toOptions;
    private String inputFile;
    private String outputFile;
    private String delimiter;

    private boolean shouldLookForFromOptions = false;
    private boolean shouldLookForToOptions = false;

    public InputParser(){
        this.currentFlag = null;
        this.fromRepresentation = null;
        this.toRepresentation = null;
        this.fromOptions = new ArrayList<>();
        this.toOptions = new ArrayList<>();
        this.inputFile = "";
        this.outputFile = "";
        this.delimiter = "";
    }

    public ParserResult parse(String[] input) throws InputParsingException {
        assertInputNonEmpty(input);

        var optionsFound = false;
        for (var argument : input) {
            if (shouldLookForFromOptions || shouldLookForToOptions) {
                optionsFound = parseOptions(argument);
            }
            if (!optionsFound) {
                resetLookForOptionsFlags();
                if (Objects.isNull(currentFlag) &&
                        (argument.startsWith(ParserConstants.SHORT_FLAG_START) || argument.startsWith(ParserConstants.LONG_FLAG_START))) {
                    parseFlag(argument);
                    if (checkHelpFlag(input)) {
                        break;
                    }
                } else if (Objects.nonNull(currentFlag)) {
                    parseValue(argument);
                } else {
                    throw new InputParsingException(String.format("Argument %s not allowed here", argument));
                }
            }
            optionsFound = false;
        }

        var shouldPrintHelp = Objects.nonNull(currentFlag) && currentFlag.equals(Flag.HELP);
        if (!shouldPrintHelp &&
                (Objects.nonNull(currentFlag) || Objects.isNull(fromRepresentation) || Objects.isNull(toRepresentation))) {
            throw new InputParsingException("Missing value for one of the switches.");
        }

        var result = new ParserResult(fromRepresentation, toRepresentation, fromOptions, toOptions, inputFile,
                outputFile, delimiter, shouldPrintHelp);
        clearAttributes();
        return result;
    }

    private void parseFlag(String argument) throws InputParsingException {
        // try to resolve short version of flag
        currentFlag = Flag.getByShort(argument);
        if (Objects.nonNull(currentFlag)) {
            return;
        }

        // try to resolve help flag since it is the only flag without value
        currentFlag = Flag.getByLong(argument);
        if (Objects.nonNull(currentFlag) && currentFlag.equals(Flag.HELP)) {
            return;
        }

        // long versions of flags are in format: --flag=value
        var splitArgument= argument.split(ParserConstants.LONG_ATTR_DELIMITER);
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
        if (currentFlag.equals(Flag.FROM) && Objects.isNull(fromRepresentation) && checkFormat(argument)) {
            fromRepresentation = Format.fromString(argument);
            shouldLookForFromOptions = true;
        } else if (currentFlag.equals(Flag.TO) && Objects.isNull(toRepresentation) && checkFormat(argument)) {
            toRepresentation = Format.fromString(argument);
            shouldLookForToOptions = true;
        } else if (currentFlag.equals(Flag.INPUT_FILE) && inputFile.isEmpty()) {
            inputFile = argument;
        } else if (currentFlag.equals(Flag.OUTPUT_FILE) && outputFile.isEmpty()) {
            outputFile = argument;
        } else if (currentFlag.equals(Flag.DELIMITER) && delimiter.isEmpty()) {
            delimiter = argument;
        } else {
            throw new InputParsingException("Invalid or duplicate value for one of the arguments.");
        }
        currentFlag = null;
    }

    private boolean parseOptions(String argument) throws InputParsingException {
        // look for from options
        if (argument.startsWith(Flag.FROM_OPTIONS.getLongVersion())) {
            if (!shouldLookForFromOptions) {
                throw new InputParsingException("From options not allowed here");
            }
            var argumentParts = argument.split(ParserConstants.LONG_ATTR_DELIMITER);
            if (argumentParts.length == 2 && resolveFromOptions(argumentParts[1])) {
                return true;
            } else {
                throw new InputParsingException(String.format("Invalid option encountered: %s", argument));
            }
        }
        // look for to options
        if (argument.startsWith(Flag.TO_OPTIONS.getLongVersion())) {
            if (!shouldLookForToOptions) {
                throw new InputParsingException("To options not allowed here");
            }
            var argumentParts = argument.split(ParserConstants.LONG_ATTR_DELIMITER);
            if (argumentParts.length == 2 && resolveToOptions(argumentParts[1])) {
                return true;
            } else {
                throw new InputParsingException(String.format("Invalid option encountered: %s", argument));
            }
        }
        return false;
    }

    private boolean checkFormat(String format) {
        return Format.contains(format);
    }

    private boolean isHelpFlagAllowed(String[] input) {
        return input.length == 1;
    }

    private boolean checkHelpFlag(String[] input) throws InputParsingException {
        if (Objects.nonNull(currentFlag) && currentFlag.equals(Flag.HELP)) {
            if (isHelpFlagAllowed(input)) {
                return true;
            }
            throw new InputParsingException("Help argument is not allowed here.");
        }
        return false;
    }

    private boolean resolveFromOptions(String option) {
        if (fromRepresentation.equals(Format.INT) && IntOption.contains(option)) {
            fromOptions.add(IntOption.fromString(option));
            return true;
        } else if (fromRepresentation.equals(Format.BITS) && BitsOption.contains(option)) {
            fromOptions.add(BitsOption.fromString(option));
            return true;
        }
        return false;
    }

    private boolean resolveToOptions(String option) {
        if (toRepresentation.equals(Format.INT)) {
            toOptions.add(IntOption.fromString(option));
            return true;
        }
        return false;
    }

    private void assertInputNonEmpty(String[] input) throws InputParsingException {
        if (input.length == 0) {
            throw new InputParsingException("Cannot run program without any arguments.");
        }
    }

    private void resetLookForOptionsFlags() {
        shouldLookForFromOptions = false;
        shouldLookForToOptions = false;
    }

    private void clearAttributes() {
        currentFlag = null;
        fromRepresentation = null;
        toRepresentation = null;
        fromOptions = new ArrayList<>();
        toOptions = new ArrayList<>();
        inputFile = "";
        outputFile = "";
        delimiter = "";
        resetLookForOptionsFlags();
    }
}
