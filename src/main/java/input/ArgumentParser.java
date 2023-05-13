package input;

import exceptions.InputParsingException;
import options.ArrayOption;
import options.BitsOption;
import format.Format;
import options.IOption;
import options.IntOption;
import utils.Flag;

import java.util.Arrays;
import java.util.Objects;

import static input.ParserConstants.ERROR_INVALID_FLAG_COMBINATION;
import static input.ParserConstants.ERROR_INVALID_TO_OPTIONS_LENGTH;

/**
 * Provides basic implementation of {@link IArgumentParser} interface.
 */
public class ArgumentParser implements IArgumentParser {

    private Flag currentFlag;

    private Format fromRepresentation;
    private Format toRepresentation;
    private IOption[] fromOptions;
    private IOption[] toOptions;
    private String inputFile;
    private String outputFile;
    private String delimiter;

    public ArgumentParser() {
        setAttributesToDefault();
    }

    /**
     * This method is the implementation of {@link IArgumentParser#parse(String[])} method.
     * In this implementation allowed flags in input are:
     * -f, --from, -t, --to, --from-options, --to-options, -i, --input, -o, --output, -d, --delimiter, -h, --help
     */
    public ParserResult parse(String[] input) throws InputParsingException {
        assertInputNonEmpty(input);

        for (var argument : input) {
            parseArgument(argument);
            if (checkHelpFlag(input)) {
                break;
            }
        }

        var shouldPrintHelp = Objects.nonNull(currentFlag) && currentFlag.equals(Flag.HELP);
        if (!shouldPrintHelp){
            validateResult();
        }


        assertNoDelimiterForArrays();
        var result = new ParserResult(fromRepresentation, toRepresentation, fromOptions, toOptions, inputFile,
                outputFile, delimiter, shouldPrintHelp);
        setAttributesToDefault();
        return result;
    }

    private void parseArgument(String argument) throws InputParsingException {
        var optionsFound = false;

        if (Objects.isNull(currentFlag)) {
            optionsFound = parseOptions(argument);
        }

        if (!optionsFound) {
            if (shouldLookForFlag(argument)) {
                parseFlag(argument);
            } else if (Objects.nonNull(currentFlag)) {
                parseValue(argument);
            } else {
                throw new InputParsingException(String.format("Argument %s not allowed here", argument));
            }
        }
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
        var splitArgument = argument.split(ParserConstants.LONG_ATTR_DELIMITER);
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
        } else if (currentFlag.equals(Flag.TO) && Objects.isNull(toRepresentation) && checkFormat(argument)) {
            toRepresentation = Format.fromString(argument);
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
            var argumentParts = argument.split(ParserConstants.LONG_ATTR_DELIMITER);
            if (argumentParts.length == 2 && resolveFromOptions(argumentParts[1])) {
                return true;
            } else {
                throw new InputParsingException(String.format("Invalid option encountered: %s", argument));
            }
        }

        // look for to options
        if (argument.startsWith(Flag.TO_OPTIONS.getLongVersion())) {
            var argumentParts = argument.split(ParserConstants.LONG_ATTR_DELIMITER);
            if (argumentParts.length == 2 && resolveToOptions(argumentParts[1])) {
                return true;
            } else {
                throw new InputParsingException(String.format("Invalid option encountered: %s", argument));
            }
        }

        return false;
    }

    private boolean shouldLookForFlag(String argument) {
        return Objects.isNull(currentFlag) &&
            (argument.startsWith(ParserConstants.SHORT_FLAG_START) ||
                    argument.startsWith(ParserConstants.LONG_FLAG_START));
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
        IOption o = IntOption.fromString(option);
        if (Objects.isNull(o)) {
            o = BitsOption.fromString(option);
        }

        if (Objects.nonNull(o)) {
            fromOptions[0] = o;
            return true;
        }

        return false;
    }

    private boolean resolveToOptions(String option) {
        IOption o = IntOption.fromString(option);
        if (Objects.isNull(o)) {
            o = ArrayOption.fromString(option);
        }
        if (o instanceof ArrayOption arrayOption) {
            var optionIndex = ArrayOption.isFromFirstSet(arrayOption) ? 0 : 1;
            toOptions[optionIndex] = o;
            return true;
        } else if (Objects.nonNull(o)) {
            toOptions[0] = o;
            return true;
        }
        return false;

    }

    private void assertInputNonEmpty(String[] input) throws InputParsingException {
        if (input.length == 0) {
            throw new InputParsingException("Cannot run program without any arguments.");
        }
    }

    private void assertNoDelimiterForArrays() throws InputParsingException {
        if (!Objects.isNull(fromRepresentation) && fromRepresentation.equals(Format.ARRAY) && !delimiter.isEmpty()) {
            throw new InputParsingException("Specifying delimiter when converting from array is not allowed");
        }
    }

    private void setAttributesToDefault() {
        currentFlag = null;
        fromRepresentation = null;
        toRepresentation = null;
        fromOptions = new IOption[1];
        toOptions = new IOption[2];
        inputFile = "";
        outputFile = "";
        delimiter = "";
    }

    private void checkHasInputAndOutputFormat() throws InputParsingException {
        if (Objects.isNull(fromRepresentation) || Objects.isNull(toRepresentation)) {
            throw new InputParsingException("Missing value for one of the switches.");
        }
    }

    private void checkHasToOptionAndIsRightFormat() throws InputParsingException {
        if (toOptions.length < 2) {
            throw new InputParsingException(ERROR_INVALID_TO_OPTIONS_LENGTH);
        }
        IOption o = toOptions[0];
        if (Objects.nonNull(o) && toRepresentation != Format.INT && toRepresentation != Format.ARRAY) {
            throw new InputParsingException(ERROR_INVALID_FLAG_COMBINATION);
        }
    }

    private void checkHasFromOptionAndIsRightFormat() throws InputParsingException {
        if (fromOptions.length < 1) {
            throw new InputParsingException(ERROR_INVALID_TO_OPTIONS_LENGTH);
        }
        IOption o = fromOptions[0];
        if (Objects.nonNull(o) && fromRepresentation != Format.INT && fromRepresentation != Format.BITS) {
            throw new InputParsingException(ERROR_INVALID_FLAG_COMBINATION);
        }
    }

    private void checkIntOptionIsBigOrLittle(Format representation, IOption[] options) throws InputParsingException {
        if (representation != Format.INT) {
            return;
        }

        if (options.length < 1) {
            throw new InputParsingException(ERROR_INVALID_TO_OPTIONS_LENGTH);
        }

        IOption o = options[0];
        if (Objects.nonNull(o) && o != IntOption.BIG && o != IntOption.LITTLE) {
            throw new InputParsingException(ERROR_INVALID_FLAG_COMBINATION);
        }
    }

    private void checkExistingBitsInputFromToOptionIsLeftOrRight() throws InputParsingException {
        if (fromRepresentation != Format.BITS) {
            return;
        }

        if (fromOptions.length < 1) {
            throw new InputParsingException(ERROR_INVALID_TO_OPTIONS_LENGTH);
        }

        IOption o = fromOptions[0];
        if (Objects.nonNull(o) && o != BitsOption.LEFT && o != BitsOption.RIGHT) {
            throw new InputParsingException(ERROR_INVALID_FLAG_COMBINATION);
        }
    }

    private void checkArrayOutputFormat() throws InputParsingException {
        if (toRepresentation != Format.ARRAY) {
            return;
        }

        if (toOptions.length < 2) {
            throw new InputParsingException(ERROR_INVALID_TO_OPTIONS_LENGTH);
        }

        IOption o = toOptions[1];

        if (Objects.nonNull(o) && Arrays.stream(toOptions).noneMatch(option ->
                Objects.isNull(option) ||
                        option.equals(ArrayOption.CHARACTERS) ||
                        option.equals(ArrayOption.ZEROX_PREFIXED_HEX_NUMBER) ||
                        option.equals(ArrayOption.DECIMAL_NUMBER) ||
                        option.equals(ArrayOption.ZEROB_PREFIXED_BINARY_NUMBER))) {
            throw new InputParsingException(ERROR_INVALID_FLAG_COMBINATION);
        }
    }

    private void validateResult() throws InputParsingException {
        checkHasInputAndOutputFormat();
        checkHasToOptionAndIsRightFormat();
        checkHasFromOptionAndIsRightFormat();
        checkIntOptionIsBigOrLittle(fromRepresentation, fromOptions);
        checkIntOptionIsBigOrLittle(toRepresentation, toOptions);
        checkExistingBitsInputFromToOptionIsLeftOrRight();
        checkArrayOutputFormat();
    }
}
