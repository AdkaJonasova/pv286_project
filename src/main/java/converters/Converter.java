package converters;

import exceptions.ConverterException;
import options.BitsOption;
import options.IOption;

import static options.BitsOption.LEFT;

/**
 * The Converter abstract class provides a template for converting between binary data and other formats.
 * Subclasses of Converter must implement the {@link #convertTo(String, IOption[])}
 * and {@link #convertFrom(String, IOption[])} methods to provide specific
 * conversion functionality.
 */
public abstract class Converter {
	protected static final int BYTE_LENGTH = 8;
	protected static final int HEXADECIMAL = 16;
	/**
	 * Converts a binary string to another string format using the specified options.
	 *
	 * @param bitStr the binary string to convert
	 * @param options an array of options that affect the conversion process
	 * @return the converted string into specific format
	 * @throws ConverterException if an error occurs during conversion
	 */
	public abstract String convertTo(String bitStr, IOption[] options) throws ConverterException;

	/**
	 * Converts a string in another format to a binary string using the specified options.
	 *
	 * @param input the input string to convert
	 * @param options an array of options that affect the conversion process
	 * @return the converted binary string
	 * @throws ConverterException if an error occurs during conversion
	 */
	public abstract String convertFrom(String input, IOption[] options) throws ConverterException;

	/**
	 * Adds missing zero bits to a binary string so that it is padded to a multiple of byte.
	 * PadSize is set to {@link BitsOption#LEFT}
	 *
	 * @param bitStr the binary string to pad with zero bits
	 * @return the padded binary string
	 */
	protected static String addMissingZerosToBitString(String bitStr) {
		return addMissingZerosToBitString(bitStr, LEFT);
	}

	/**
	 * Adds missing zero bits to a binary string so that it is padded to a multiple of byte.
	 *
	 * @param bitStr the binary string to pad with zero bits
	 * @param padSide the side to pad the binary string on (either {@link BitsOption#LEFT} or {@link BitsOption#RIGHT})
	 * @return the padded binary string
	 */
	protected static String addMissingZerosToBitString(String bitStr, BitsOption padSide) {
		int numOfMissingBits = bitStr.length() % BYTE_LENGTH;

		if (numOfMissingBits != 0) {
			int padding = BYTE_LENGTH - (numOfMissingBits);
			StringBuilder sb = new StringBuilder();
			if (LEFT.equals(padSide)) {
				sb.append("0".repeat(padding));
				sb.append(bitStr);
				return sb.toString();
			}
			sb.append(bitStr);
			sb.append("0".repeat(padding));
			return sb.toString();
		}
		return bitStr;
	}

	/**
	 * Validates that the input string matches the specified regular expression.
	 *
	 * @param input the input string to validate
	 * @param regex the regular expression to use for validation
	 * @throws ConverterException if the input string does not match the regular expression
	 */
	protected static void validateInput(String input, String regex) throws ConverterException {
		if (!input.matches(regex)){
			throw new ConverterException(String.format("Invalid input format: %s", input));
		}
	}
}
