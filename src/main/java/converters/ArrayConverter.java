package converters;

import exceptions.ConverterException;
import options.ArrayOption;
import options.BitsOption;
import options.HexOption;

import java.util.Arrays;
import java.util.List;

import static options.ArrayOption.*;

public class ArrayConverter extends Converter<ArrayOption> {
	@Override
	public String convertTo(String bitStr, List<ArrayOption> options) throws ConverterException {
		if(!validateInput(bitStr, "^[0-1 ]+$")){
			throw new ConverterException(String.format("Invalid input format: %s", bitStr));
		}

		ArrayOption representation = ArrayOption.getLastRepresentationOption(options);
		ArrayOption bracket = ArrayOption.getLastBracketOption(options);

		bitStr = addMissingZerosToBitString(bitStr);

		int byteArrayLength = bitStr.length() / 8;
		String[] byteArray = new String[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			int startIndex = i * 8;
			int endIndex = startIndex + 8;
			String byteString = bitStr.substring(startIndex, endIndex);

			if (representation.equals(ZEROX_PREFIXED_HEX_NUMBER)){
				String byteValue = new HexConverter().convertTo(byteString, List.of(HexOption.SHORT));
				byteArray[i] = "0x" + byteValue;
			} else if (representation.equals(DECIMAL_NUMBER)){
				String byteValue = new IntConverter().convertTo(byteString, null);
				byteArray[i] = byteValue;
			} else if (representation.equals(ZEROB_PREFIXED_BINARY_NUMBER)) {
				String byteValue = new BitsConverter().convertTo(byteString, List.of(BitsOption.SHORT));
				byteArray[i] = "0b" + byteValue;
			} else {
				String byteValue = new HexConverter().convertTo(byteString, null);
				byteArray[i] = "'\\x" + byteValue + "'";
			}
		}

		String result = String.join(", ", byteArray);

		if (LEFT_CURLY_BRACKETS.equals(bracket) || RIGHT_CURLY_BRACKETS.equals(bracket) || CURLY_BRACKETS.equals(bracket)){
			return "{" + result + "}";
		} else if (LEFT_SQUARE_BRACKETS.equals(bracket) || RIGHT_SQUARE_BRACKETS.equals(bracket) || SQUARE_BRACKETS.equals(bracket)){
			return "[" + result + "]";
		}

		return "(" + result + ")";
	}

	@Override
	public String convertFrom(String input, List<ArrayOption> options) {
		return null;
	}

}
