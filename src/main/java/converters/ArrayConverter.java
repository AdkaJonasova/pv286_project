package converters;

import exceptions.ConverterException;
import format.Format;
import options.ArrayOption;
import options.BitsOption;
import options.HexOption;
import options.IOption;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static format.Format.BYTES;
import static options.ArrayOption.*;

public class ArrayConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");

		ArrayOption representation = getRepresentationOption(options);
		ArrayOption bracket = getBracketOption(options);

		bitStr = addMissingZerosToBitString(bitStr);

		int byteArrayLength = bitStr.length() / 8;
		String[] byteArray = new String[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			int startIndex = i * 8;
			int endIndex = startIndex + 8;
			String byteString = bitStr.substring(startIndex, endIndex);

			if (representation.equals(ZEROX_PREFIXED_HEX_NUMBER)) {
				String byteValue = new HexConverter().convertTo(byteString, new IOption[] { HexOption.SHORT });
				byteArray[i] = "0x" + byteValue;
			} else if (representation.equals(DECIMAL_NUMBER)) {
				String byteValue = new IntConverter().convertTo(byteString, null);
				byteArray[i] = byteValue;
			} else if (representation.equals(ZEROB_PREFIXED_BINARY_NUMBER)) {
				String byteValue = new BitsConverter().convertTo(byteString, new IOption[] { BitsOption.SHORT });
				byteArray[i] = "0b" + byteValue;
			} else {
				String byteValue = new HexConverter().convertTo(byteString, null);
				byteArray[i] = "'\\x" + byteValue + "'";
			}
		}

		String result = String.join(", ", byteArray);

		return bracket.getOpen() + result + bracket.getClose();
	}

	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		input = input.substring(1, input.length() - 1);
		input = input.replace(" ", "");

		String[] values = input.split(",");
		
		StringBuilder builder = new StringBuilder();

		for (String value : values) {
			Format format = Format.getFormatFromInputValue(value);
			Pattern regex = Pattern.compile(format.getArrayRegex());
			Matcher matcher = regex.matcher(value);

			if (matcher.find()) {
				String valueToConvert = matcher.group(1);

				if(BYTES.equals(format)){
					String hexValue = "\\u00" + valueToConvert;
					valueToConvert = String.format("%c", Integer.parseInt(hexValue.substring(2), 16));
				}

				builder.append(format.getConverter().convertFrom(valueToConvert, null));
			}
		}

		return builder.toString();
	}

	private ArrayOption getRepresentationOption(IOption[] options) {
		if (Objects.isNull(options))
			return ZEROX_PREFIXED_HEX_NUMBER;
		for (var option : options) {
			if (Objects.nonNull(option) && ArrayOption.isFromFirstSet((ArrayOption) option)) {
				return (ArrayOption) option;
			}
		}
		return ZEROX_PREFIXED_HEX_NUMBER;
	}

	private ArrayOption getBracketOption(IOption[] options) {
		if (Objects.isNull(options))
			return CURLY_BRACKETS;

		for (var option : options) {
			if (Objects.nonNull(option) && ArrayOption.isFromSecondSet((ArrayOption) option)) {
				return (ArrayOption) option;
			}
		}
		return CURLY_BRACKETS;
	}

}
