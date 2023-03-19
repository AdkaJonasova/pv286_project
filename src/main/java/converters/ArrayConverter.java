package converters;

import options.ArrayOption;

import java.util.Arrays;

import static options.ArrayOption.*;

public class ArrayConverter extends Converter<ArrayOption> {
	@Override
	public String convertTo(String bitStr, ArrayOption option) {
		bitStr = addMissingZerosToBitString(bitStr);

		int byteArrayLength = bitStr.length() / 8;
		String[] byteArray = new String[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			int startIndex = i * 8;
			int endIndex = startIndex + 8;
			String byteString = bitStr.substring(startIndex, endIndex);
			String byteValue = new HexConverter().convertTo(byteString);
			byteArray[i] = "0x" + (byteValue.startsWith("0") ? byteValue.charAt(1) : byteValue);
		}

		String result = Arrays.toString(byteArray);
		result = result.replace(LEFT_SQUARE_BRACKETS.getText(), LEFT_CURLY_BRACKETS.getText());
		result = result.replace(RIGHT_SQUARE_BRACKETS.getText(), RIGHT_CURLY_BRACKETS.getText());
		return result;
	}

	@Override
	public String convertFrom(String input, ArrayOption option) {
		return null;
	}
}
