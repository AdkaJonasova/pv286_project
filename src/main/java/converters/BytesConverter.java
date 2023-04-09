package converters;

import exceptions.ConverterException;
import options.IOption;

/**
 * This class provides methods for converting between strings of bytes and binary strings.
 * It extends the abstract {@link Converter} class.
 * <p>
 * To convert a string of bytes to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to a string of bytes, use the {@link #convertFrom(String, IOption[])} method.
 * <p>
 * The BytesConverter doesn't support any options
 */
public class BytesConverter extends Converter {

	/**
	 * Converts a binary string to a string of bytes.
	 *
	 * @param bitStr  the binary string to convert
	 * @param options an array of {@link IOption} options (options are ignored)
	 * @return the string of bytes
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		return this.convertTo(bitStr);
	}

	/**
	 * Converts a binary string to a string of bytes.
	 *
	 * @param bitStr  the binary string to convert
	 * @return the string of bytes
	 * @throws ConverterException if the input binary string is invalid
	 */
	public String convertTo(String bitStr) throws ConverterException {
		validateInput(bitStr, "^[01]+$");

		String updatedBitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < updatedBitStr.length(); i += BYTE_LENGTH) {
			String byteString = updatedBitStr.substring(i, Math.min(i + BYTE_LENGTH, updatedBitStr.length()));
			int byteValue = Integer.parseInt(byteString, 2);

			builder.append((char) byteValue);
		}
		return builder.toString();
	}

	/**
	 * Converts a string of bytes to a binary string.
	 *
	 * @param input   the string of bytes to convert
	 * @param options an array of {@link IOption} options (options are ignored)
	 * @return the binary string
	 */
	@Override
	public String convertFrom(String input, IOption[] options) {
		return this.convertFrom(input);
	}

	/**
	 * Converts a string of bytes to a binary string.
	 *
	 * @param input the string of bytes to convert
	 * @return the binary string
	 */
	public String convertFrom(String input) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			StringBuilder binaryBuilder = new StringBuilder();
			int charValue = input.charAt(i);
			while (charValue > 0) {
				binaryBuilder.append(charValue % 2);
				charValue /= 2;
			}
			while (binaryBuilder.length() < BYTE_LENGTH) {
				binaryBuilder.append(0);
			}
			builder.append(binaryBuilder.reverse());
		}
		return builder.toString();
	}
}
