package converters;

public class BytesConverter extends Converter {
	@Override
	public String convertTo(String bitStr, String option) {
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
	public String convertFrom(String str, String option) {
		return this.convertFrom(str);
	}

	public String convertFrom(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			StringBuilder binaryBuilder = new StringBuilder();
			int charValue = str.charAt(i);
			while (charValue > 0) {
				binaryBuilder.append(charValue % 2);
				charValue /= 2;
			}
			while (binaryBuilder.length() < 8) {
				binaryBuilder.append(0);
			}
			result.append(binaryBuilder.reverse());
		}
		return result.toString();
	}
}
