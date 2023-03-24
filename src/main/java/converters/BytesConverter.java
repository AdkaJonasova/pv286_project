package converters;

import exceptions.ConverterException;
import options.IOption;

import java.util.List;

public class BytesConverter extends Converter<IOption> {
	@Override
	public String convertTo(String bitStr, List<IOption> options) throws ConverterException {
		return this.convertTo(bitStr);
	}

	public String convertTo(String bitStr) throws ConverterException {
		if(!validateInput(bitStr, "^[0-1 ]+$")){
			throw new ConverterException(String.format("Invalid input format: %s", bitStr));
		}

		bitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bitStr.length(); i += 8) {
			String byteString = bitStr.substring(i, Math.min(i + 8, bitStr.length()));
			int byteValue = Integer.parseInt(byteString, 2);

			builder.append((char) byteValue);
		}
		return builder.toString();
	}

	@Override
	public String convertFrom(String input, List<IOption> options) {
		return this.convertFrom(input);
	}

	public String convertFrom(String input) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			StringBuilder binaryBuilder = new StringBuilder();
			int charValue = input.charAt(i);
			while (charValue > 0) {
				binaryBuilder.append(charValue % 2);
				charValue /= 2;
			}
			while (binaryBuilder.length() < 8) {
				binaryBuilder.append(0);
			}
			builder.append(binaryBuilder.reverse());
		}
		return builder.toString();
	}
}
