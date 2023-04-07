package converters;

import exceptions.ConverterException;
import options.HexOption;
import options.IOption;

import java.util.Objects;

import static options.HexOption.LONG;
import static options.HexOption.SHORT;

/**
 * This class provides methods for converting between hex value strings and binary strings.
 * It extends the abstract Converter class.
 * <p>
 * To convert a hex value string to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to a hex value string, use the {@link #convertFrom(String, IOption[])} method.
 * <p>
 * The HexConverter supports the following options:
 * <ul>
 *     <li>{@link HexOption#LONG}: normal format (default)</li>
 *     <li>{@link HexOption#SHORT}: first 0 is omitted if presented</li>
 * </ul>
 */
public class HexConverter extends Converter {

	/**
	 * Converts a binary string to a hex value string.
	 *
	 * @param bitStr   the binary string to convert
	 * @param options  an array of {@link HexOption} options (first is taken,
	 *                    if none is provided, {@link HexOption#LONG} is used)
	 * @return the hex value string
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");
		HexOption version = getVersionFromOptions(options);

		String updatedBitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < updatedBitStr.length(); i += 4) {
			String nibble = updatedBitStr.substring(i, i + 4);
			int value = Integer.parseInt(nibble, 2);
			builder.append(Integer.toHexString(value));
		}

		String result = builder.toString();
		return SHORT.equals(version) && result.startsWith("0") ? result.substring(1) : result;
	}

	/**
	 * Converts a hex value string to a binary string.
	 *
	 * @param input    the hex value string to convert
	 * @param options  an array of {@link IOption} options (options are ignored)
	 * @return the binary string
	 * @throws ConverterException if the input hex value string is invalid
	 */
	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		return this.convertFrom(input);
	}

	/**
	 * Converts a hex value string to a binary string.
	 *
	 * @param input    the hex value string to convert
	 * @return the binary string
	 * @throws ConverterException if the input hex value string is invalid
	 */
	public String convertFrom(String input) throws ConverterException {
		validateInput(input, "^([0-9a-fA-F]{2}\\s?)+$");

		String updatedInput = input.replace(" ", "");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < updatedInput.length(); i++) {
			String binaryString = String.format("%4s",
							Integer.toBinaryString(Character.digit(updatedInput.charAt(i), 16)))
					.replace(' ', '0');
			builder.append(binaryString);
		}
		return builder.toString();
	}

	private HexOption getVersionFromOptions(IOption[] options) throws ConverterException {
		try {
			return Objects.isNull(options) || options.length == 0 || options[0] == null ? LONG : (HexOption) options[0];
		} catch (ClassCastException e) {
			throw new ConverterException(String.format("HexConverter doesn't support option: %s", options[0]));
		}
	}
}
