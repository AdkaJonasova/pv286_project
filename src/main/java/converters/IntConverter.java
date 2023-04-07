package converters;

import exceptions.ConverterException;
import options.IOption;
import options.IntOption;

import java.util.Objects;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

/**
 * This class provides methods for converting between unsigned integer value strings and binary strings.
 * It extends the abstract Converter class.
 * <p>
 * To convert an unsigned integer string to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to an unsigned integer value string, use the {@link #convertFrom(String, IOption[])} method.
 * <p>
 * The IntConverter supports the following options:
 * <ul>
 *     <li>{@link IntOption#BIG}: big-endian format (default)</li>
 *     <li>{@link IntOption#LITTLE}: little-endian format</li>
 * </ul>
 */
public class IntConverter extends Converter {

	/**
	 * Converts a binary string to an unsigned integer value string.
	 *
	 * @param bitStr   the binary string to convert
	 * @param options  an array of {@link IntOption} options (first is taken, if none is provided, {@link IntOption#BIG} is used)
	 * @return the unsigned integer value string
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");
		IntOption endian = getEndianFromOptions(options);

		bitStr = processBitString(bitStr, endian);
		long unsignedInt = Long.parseUnsignedLong(bitStr, 2);

		return Long.toUnsignedString(unsignedInt);
	}

	/**
	 * Converts an unsigned integer value string to a binary string.
	 *
	 * @param input    the unsigned integer value string to convert
	 * @param options  an array of {@link IntOption} options (first is taken, if none is provided, {@link IntOption#BIG} is used)
	 * @return the binary string
	 * @throws ConverterException if the input unsigned integer value string is invalid
	 */
	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		validateInput(input, "^\\d+$");
		IntOption endian = getEndianFromOptions(options);

		long unsignedInt = Long.parseUnsignedLong(input);
		String bitStr = Long.toBinaryString(unsignedInt);
		bitStr = processBitString(bitStr, endian);

		return bitStr;
	}

	private String processBitString(String bitStr, IntOption endian) {
		bitStr = addMissingZerosToBitString(bitStr);
		if (LITTLE.equals(endian)) {
			bitStr = changeEndian(bitStr);
		}
		return bitStr;
	}

	private String changeEndian(String bitStr) {
		StringBuilder builder = new StringBuilder();
		for (int i = bitStr.length() - 1; i >= 0; i -= 8) {
			int startIndex = Math.max(i - 7, 0);
			String byteStr = bitStr.substring(startIndex, i + 1);
			builder.append(byteStr);
		}
		return builder.toString();
	}

	private IntOption getEndianFromOptions(IOption[] options) throws ConverterException {
		try {
			return Objects.isNull(options) || options.length == 0 || options[0] == null ? BIG : (IntOption) options[0];
		} catch (ClassCastException e) {
			throw new ConverterException(String.format("IntConverter doesn't support option: %s", options[0]));
		}
	}
}
