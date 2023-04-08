package converters;

import exceptions.ConverterException;
import format.Format;
import options.BitsOption;
import options.HexOption;
import options.IOption;
import options.ArrayOption;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import static format.Format.ARRAY;
import static format.Format.BYTES;
import static options.ArrayOption.ZEROX_PREFIXED_HEX_NUMBER;
import static options.ArrayOption.DECIMAL_NUMBER;
import static options.ArrayOption.SQUARE_BRACKETS;
import static options.ArrayOption.ZEROB_PREFIXED_BINARY_NUMBER;
import static options.ArrayOption.CURLY_BRACKETS;
import static options.ArrayOption.REGULAR_BRACKETS;


/**
 * This class provides methods for converting between byte array strings and binary strings.
 * It extends the abstract {@link Converter} class.
 * <p>
 * To convert a byte array string to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to a byte array string, use the {@link #convertFrom(String, IOption[])} method.
 * <p>
 * The ArrayConverter supports the following options:
 * <ul>
 *     <li>{@link ArrayOption#ZEROB_PREFIXED_BINARY_NUMBER}: represent bytes as a 0x-prefixed hex number (default)</li>
 *     <li>{@link ArrayOption#DECIMAL_NUMBER}: represent bytes as a decimal number</li>
 *     <li>{@link ArrayOption#CHARACTERS}: represent bytes as characters</li>
 *     <li>{@link ArrayOption#ZEROB_PREFIXED_BINARY_NUMBER}: represent bytes as 0b-prefixed binary number</li>
 *     <li>{@link ArrayOption#CURLY_BRACKETS}: use curly brackets in output (default)</li>
 *     <li>{@link ArrayOption#SQUARE_BRACKETS}: use square brackets in output</li>
 *     <li>{@link ArrayOption#REGULAR_BRACKETS}: use regular brackets in output</li>
 * </ul>
 */
public class ArrayConverter extends Converter {
    private static class Pair {

        Pair(int index, String value) {
            this.index = index;
            this.value = value;
        }

        int index;
        String value;
    }

    /**
     * Converts a binary string to a byte array string.
     *
     * @param bitStr   the binary string to convert
     * @param options  an array of {@link ArrayOption} options (first two are taken, if none is provided,
     * {@link ArrayOption#ZEROB_PREFIXED_BINARY_NUMBER} and {@link ArrayOption#CURLY_BRACKETS} is used)
     * @return the byte array string
     * @throws ConverterException if the input binary string is invalid
     */
    @Override
    public String convertTo(String bitStr, IOption[] options) throws ConverterException {
        String result = convertToWithoutBrackets(bitStr, options);

        ArrayOption bracket = getBracketOption(options);
        return bracket.getOpen() + result + bracket.getClose();
    }

    private String convertToWithoutBrackets(String bitStr, IOption[] options) throws ConverterException {
        validateInput(bitStr, "^[01 ]+$");

        ArrayOption representation = getRepresentationOption(options);

        String input = addMissingZerosToBitString(bitStr);

        int byteArrayLength = input.length() / BYTE_LENGTH;
        String[] byteArray = new String[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            int startIndex = i * BYTE_LENGTH;
            int endIndex = startIndex + BYTE_LENGTH;
            String byteString = input.substring(startIndex, endIndex);

            if (representation.equals(ZEROX_PREFIXED_HEX_NUMBER)) {
                String byteValue = new HexConverter().convertTo(byteString, new IOption[]{HexOption.SHORT});
                byteArray[i] = "0x" + byteValue;
            } else if (representation.equals(DECIMAL_NUMBER)) {
                String byteValue = new IntConverter().convertTo(byteString, null);
                byteArray[i] = byteValue;
            } else if (representation.equals(ZEROB_PREFIXED_BINARY_NUMBER)) {
                String byteValue = new BitsConverter().convertTo(byteString, new IOption[]{BitsOption.SHORT});
                byteArray[i] = "0b" + byteValue;
            } else {
                String byteValue = new HexConverter().convertTo(byteString, null);
                byteArray[i] = "'\\x" + byteValue + "'";
            }
        }
        return String.join(", ", byteArray);
    }

    /**
     * Converts a byte array string to a binary string.
     *
     * @param input    the byte array string to convert
     * @param options  an array of {@link ArrayOption} options (options are ignored)
     * @return the binary string
     * @throws ConverterException if the input byte array string is invalid
     */
    @Override
    public String convertFrom(String input, IOption[] options) throws ConverterException {
        validateArrayInput(input);
        String result = input.substring(1, input.length() - 1);
        result = removeWhiteSpaces(result);

        return convertFromWithoutBrackets(result);
    }

    /**
     * Converts a nested byte array string to a nested byte array string.
     *
     * @param input    the nested byte array string to convert
     * @param options  an array of {@link ArrayOption} options (first two are taken,
     *                 if none is provided, {@link ArrayOption#ZEROB_PREFIXED_BINARY_NUMBER}
     *                 and {@link ArrayOption#CURLY_BRACKETS} is used)
     * @return the nested byte array string
     * @throws ConverterException if the input nested byte array string is invalid
     */
    public String convertFromArrayToArray(String input, IOption[] options) throws ConverterException {
        String result = removeWhiteSpaces(input);
        validateArrayToArrayInput(input);

        ArrayOption bracketOption = getBracketOption(options);
        String unitedClosureInput = uniteClosures(result, bracketOption);
        result = convertArrayToArray(unitedClosureInput, 0,
                bracketOption.getOpen(), bracketOption.getClose(), options).value;
        return result;
    }

    private static String convertFromWithoutBrackets(String input) throws ConverterException {
        String[] values = input.split(",");

        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            builder.append(convertFromValue(value));
        }

        return builder.toString();
    }

    private static String convertFromValue(String value) throws ConverterException {
        Format format = Format.getFormatFromInputValue(value);
        if (format == null) {
            throw new ConverterException(String.format("Invalid value: %s", value));
        }
        if (ARRAY.equals(format)){
            return "";
        }

        Pattern regex = Pattern.compile(format.getArrayRegex());
        Matcher matcher = regex.matcher(value);

        if (matcher.find()) {
            String valueToConvert = matcher.group(matcher.groupCount());

            if (BYTES.equals(format)) {
                String hexValue = "\\u00" + valueToConvert;
                valueToConvert = String.format("%c", Integer.parseInt(hexValue.substring(2), HEXADECIMAL));
            }

            return format.getConverter().convertFrom(valueToConvert, null);
        }
        return value;
    }

    private void validateArrayInput(String input) throws ConverterException {
        if (Objects.isNull(input) || input.length() < 2) {
            throw new ConverterException(String.format("Invalid input: %s", input));
        }
        checkBrackets(String.valueOf(input.charAt(0)), String.valueOf(input.charAt(input.length() - 1)));
    }

    private void checkBrackets(String openingBracket, String closingBracket) throws ConverterException {
        String closures = openingBracket + closingBracket;
        List<String> correctBracketsOptions = List.of(CURLY_BRACKETS.getText(),
                SQUARE_BRACKETS.getText(),
                REGULAR_BRACKETS.getText());
        if (!correctBracketsOptions.contains(closures)) {
            throw new ConverterException(String.format("Non-equal brackets: %s", closingBracket));
        }
    }


    private Pair convertArrayToArray(String input, int inputActualPosition, String openBrackets,
                                     String closingBracket, IOption[] options) throws ConverterException {
        int index = inputActualPosition;
        List<String> values = new ArrayList<>();

        StringBuilder valueToConvert = new StringBuilder();
        String stringValue;
        while (index < input.length()) {
            String character = String.valueOf(input.charAt(index));
            if (character.equals(openBrackets)) {
                Pair semiValues = convertArrayToArray(input, index + 1, openBrackets, closingBracket, options);
                index = semiValues.index;
                values.add(semiValues.value);
            } else if (character.equals(closingBracket)) {
                if (!valueToConvert.isEmpty()) {
                    String resultChar = getResultChar(options, valueToConvert.toString());
                    values.add(resultChar);
                }
                stringValue = String.format("%s%s%s", openBrackets, String.join(", ", values), closingBracket);
                return new Pair(index, stringValue);
            } else if (character.equals(",")) {
                if (!valueToConvert.isEmpty()) {
                    String resultChar = getResultChar(options, valueToConvert.toString());
                    values.add(resultChar);
                    valueToConvert = new StringBuilder();
                }
            } else {
                valueToConvert.append(character);
            }
            index++;
        }

        stringValue = values.isEmpty() ? "" : values.get(0);
        return new Pair(inputActualPosition, stringValue);
    }

    private String getResultChar(IOption[] options, String valueToParse) throws ConverterException {
        if (valueToParse.equals("")){
            return "";
        }

        String bitValue = convertFromValue(valueToParse);
        return convertToWithoutBrackets(bitValue, options);
    }

    private void validateArrayToArrayInput(String input) throws ConverterException {
        List<String> openingBrackets = List.of(
                CURLY_BRACKETS.getOpen(),
                SQUARE_BRACKETS.getOpen(),
                REGULAR_BRACKETS.getOpen()
        );
        List<String> closingBrackets = List.of(
                CURLY_BRACKETS.getClose(),
                SQUARE_BRACKETS.getClose(),
                REGULAR_BRACKETS.getClose()
        );

        if (Objects.isNull(input) || input.length() < 2 || !openingBrackets.contains(String.valueOf(input.charAt(0)))) {
            throw new ConverterException(String.format("Invalid input: %s", input));
        }

        Deque<String> stack = new ArrayDeque<>();
        for (char c : input.toCharArray()) {
            String charStringValue = String.valueOf(c);
            if (openingBrackets.contains(charStringValue)) {
                stack.push(charStringValue);
            } else if (closingBrackets.contains(charStringValue)) {
                if (stack.isEmpty()) {
                    throw new ConverterException(String.format("Missing opening bracket in input: %s", input));
                }
                checkBrackets(stack.pop(), charStringValue);
            }
        }
        if (!stack.isEmpty()) {
            throw new ConverterException(String.format("Missing closing bracket in input: %s", input));
        }
    }

    private String uniteClosures(String input, ArrayOption bracketOption) {
        String result = input.replaceAll("[({\\[]", bracketOption.getOpen());
        result = result.replaceAll("[)}\\]]", bracketOption.getClose());
        return result;
    }

    private ArrayOption getRepresentationOption(IOption[] options) throws ConverterException {
        if (Objects.isNull(options)){
            return ZEROX_PREFIXED_HEX_NUMBER;
        }

        for (var option : options) {
            try {
                if (Objects.nonNull(option) && ArrayOption.isFromFirstSet((ArrayOption) option)) {
                    return (ArrayOption) option;
                }
            } catch (ClassCastException e) {
                throw new ConverterException(String.format("ArrayConverter doesn't support option: %s", option));
            }
        }
        return ZEROX_PREFIXED_HEX_NUMBER;
    }

    private ArrayOption getBracketOption(IOption[] options) throws ConverterException {
        if (Objects.isNull(options)) {
            return CURLY_BRACKETS;
        }

        for (var option : options) {
            try {
                if (Objects.nonNull(option) && ArrayOption.isFromSecondSet((ArrayOption) option)) {
                    return (ArrayOption) option;
                }
            } catch (ClassCastException e) {
                throw new ConverterException(String.format("ArrayConverter doesn't support option: %s", option));
            }
        }
        return CURLY_BRACKETS;
    }

}
