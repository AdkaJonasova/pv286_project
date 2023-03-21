package converters;

import options.ArrayOption;
import options.BitsOption;
import options.HexOption;

import java.util.Arrays;
import java.util.List;

import static options.ArrayOption.*;

public class ArrayConverter extends Converter<ArrayOption> {
	@Override
	public String convertTo(String bitStr, List<ArrayOption> options) {
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
				byteArray[i] = "\\x" + byteValue;
			}
		}

		String result = Arrays.toString(byteArray);
		result = result.replace(LEFT_SQUARE_BRACKETS.getText(), LEFT_CURLY_BRACKETS.getText());
		result = result.replace(RIGHT_SQUARE_BRACKETS.getText(), RIGHT_CURLY_BRACKETS.getText());
		return result;
	}

	@Override
	public String convertFrom(String input, List<ArrayOption> options) {
		return convertFrom(input);
	}

	public String convertFrom(String input) {
		return null;
	}
}
