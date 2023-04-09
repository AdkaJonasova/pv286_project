package converters;

import exceptions.ConverterException;
import options.BitsOption;
import options.IOption;

import java.util.Objects;

import static options.BitsOption.LEFT;
import static options.BitsOption.SHORT;

/**
 * This class provides methods for converting between binary strings and binary strings.
 * It extends the abstract {@link Converter} class.
 * <p>
 * To convert a binary string to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to a binary string, use the {@link #convertFrom(String, IOption[])} method.
 * <p>
 * The BitsConverter supports the following options:
 * <ul>
 *     <li>{@link BitsOption#LEFT}: pad binary string from left(default)</li>
 *     <li>{@link BitsOption#RIGHT}: pad binary string from right</li>
 *     <li>{@link BitsOption#SHORT}: omitted 0 from left</li>
 * </ul>
 */
public class BitsConverter extends Converter {

	/**
	 * Converts a binary string to a binary string.
	 *
	 * @param bitStr   the binary string to convert
	 * @param options  an array of {@link BitsOption} options (first is taken,
	 *                    if none is provided, {@link BitsOption#LEFT} is used)
	 * @return the binary string
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01]+$");
		BitsOption padSide = getPadSideFromOptions(options);

		if (padSide.equals(SHORT)) {
			int index = bitStr.indexOf("1");
			if (index == -1) {
				return bitStr;
			} else {
				return bitStr.substring(index);
			}
		}

		return addMissingZerosToBitString(bitStr, padSide);
	}

	/**
	 * Converts a binary string to a binary string.
	 *
	 * @param input    the binary string to convert
	 * @param options  an array of {@link BitsOption} options (first is taken,
	 *                    if none is provided, {@link BitsOption#LEFT} is used)
	 * @return the binary string
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		String updatedInput = removeWhiteSpaces(input);
		validateInput(updatedInput, "^[01]+$");
		BitsOption padSide = getPadSideFromOptions(options);

		return addMissingZerosToBitString(updatedInput, padSide);
	}

	private BitsOption getPadSideFromOptions(IOption[] options) throws ConverterException {
		try {
			return Objects.isNull(options) ||
					options.length == 0 ||
					options[0] == null ? LEFT : (BitsOption) options[0];
		} catch (ClassCastException e) {
			throw new ConverterException(String.format("BitsConverter doesn't support option: %s", options[0]));
		}
	}

}
