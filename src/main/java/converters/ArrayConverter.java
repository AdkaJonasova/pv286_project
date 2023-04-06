package converters;

import exceptions.ConverterException;
import format.Format;
import options.ArrayOption;
import options.BitsOption;
import options.HexOption;
import options.IOption;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static format.Format.BYTES;
import static options.ArrayOption.*;

public class ArrayConverter extends Converter {

    private static class Pair {

        Pair(int index, String value) {
            this.index = index;
            this.value = value;
        }

        int index;
        String value;
    }

    @Override
    public String convertTo(String bitStr, IOption[] options) throws ConverterException {
        String result = convertToWithoutBrackets(bitStr, options);

        ArrayOption bracket = getBracketOption(options);
        return bracket.getOpen() + result + bracket.getClose();
    }

    private String convertToWithoutBrackets(String bitStr, IOption[] options) throws ConverterException {
        validateInput(bitStr, "^[01 ]+$");

        ArrayOption representation = getRepresentationOption(options);

        bitStr = addMissingZerosToBitString(bitStr);

        int byteArrayLength = bitStr.length() / 8;
        String[] byteArray = new String[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            int startIndex = i * 8;
            int endIndex = startIndex + 8;
            String byteString = bitStr.substring(startIndex, endIndex);

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

    @Override
    public String convertFrom(String input, IOption[] options) throws ConverterException {
        validateArrayInput(input);
        input = input.substring(1, input.length() - 1);
        input = input.replace(" ", "");

        return convertFromWithoutBrackets(input);
    }

    public String convertFromArrayToArray(String input, IOption[] options) throws ConverterException {
        validateArrayToArrayInput(input);

        ArrayOption bracketOption = getBracketOption(options);
        input = input.replace(" ", "");
        String unitedClosureInput = uniteClosures(input, bracketOption);
        return convertArrayToArray(unitedClosureInput, 0, bracketOption.getOpen(), bracketOption.getClose(), options).value;
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
        Pattern regex = Pattern.compile(format.getArrayRegex());
        Matcher matcher = regex.matcher(value);

        if (matcher.find()) {
            String valueToConvert = matcher.group(1);

            if (BYTES.equals(format)) {
                String hexValue = "\\u00" + valueToConvert;
                valueToConvert = String.format("%c", Integer.parseInt(hexValue.substring(2), 16));
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
        List<String> correctBracketsOptions = List.of(CURLY_BRACKETS.getText(), SQUARE_BRACKETS.getText(), REGULAR_BRACKETS.getText());
        if (!correctBracketsOptions.contains(closures)) {
            throw new ConverterException(String.format("Non-equal brackets: %s", closingBracket));
        }
    }


    private Pair convertArrayToArray(String input, int index, String openBrackets, String closingBracket, IOption[] options) throws ConverterException {
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
        return new Pair(index, stringValue);
    }

    private String getResultChar(IOption[] options, String valueToParse) throws ConverterException {
        if (valueToParse.equals("")) return "";
        String bitValue = convertFromValue(valueToParse);
        return convertToWithoutBrackets(bitValue, options);
    }

    private void validateArrayToArrayInput(String input) throws ConverterException {
        if (Objects.isNull(input) || input.length() < 2) {
            throw new ConverterException(String.format("Invalid input: %s", input));
        }
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

        Deque<String> stack = new ArrayDeque<>();
        for (char c : input.toCharArray()) {
            String cStringValue = String.valueOf(c);
            if (openingBrackets.contains(cStringValue)) {
                stack.push(cStringValue);
            } else if (closingBrackets.contains(cStringValue)) {
                if (stack.isEmpty()) {
                    throw new ConverterException(String.format("Missing opening bracket in input: %s", input));
                }
                checkBrackets(stack.pop(), cStringValue);
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
        if (Objects.isNull(options))
            return ZEROX_PREFIXED_HEX_NUMBER;
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
        if (Objects.isNull(options))
            return CURLY_BRACKETS;

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
