package converters;

import options.IOption;

public class BytesConverter extends Converter<IOption> {
	@Override
	public String convertTo(String bitStr, IOption option) {
		return this.convertTo(bitStr);
	}

	public String convertTo(String bitStr) {
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
	public String convertFrom(String input, IOption option) {
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
