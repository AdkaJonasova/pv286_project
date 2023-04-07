package converters;

import exceptions.ConverterException;
import options.IOption;
import options.IntOption;

import java.util.Objects;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

/**
 * This class provides methods for converting between unsigned integer value strings and binary strings.
 * It extends the abstract {@link Converter} class.
 * <p>
 * To convert an unsigned integer string to a binary string, use the {@link #convertTo(String, IOption[])} method.
 * To convert a binary string to an unsigned integer value string, use the
 * {@link #convertFrom(String, IOption[])} method.
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
	 * @param options  an array of {@link IntOption} options (first is taken,
	 *                    if none is provided, {@link IntOption#BIG} is used)
	 * @return the unsigned integer value string
	 * @throws ConverterException if the input binary string is invalid
	 */
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");
		IntOption endian = getEndianFromOptions(options);

		String updatedBitStr = processBitString(bitStr, endian);

		String decimalNumber = "0";
		String powerOfTwo = "1";
		for (int i = updatedBitStr.length() - 1; i >= 0; i--) {
			int digit = updatedBitStr.charAt(i) - '0';
			if (digit == 0) {
				powerOfTwo = multiplyStrings(powerOfTwo, "2");
				continue;
			}

			String product = multiplyStrings(String.valueOf(digit), powerOfTwo);
			decimalNumber = addStrings(decimalNumber, product);
			powerOfTwo = multiplyStrings(powerOfTwo, "2");
		}
		return decimalNumber;
	}

	private static String addStrings(String firstStrValue, String secondStrValue) {
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		int i = firstStrValue.length() - 1;
		int j = secondStrValue.length() - 1;
		while (i >= 0 || j >= 0 || carry > 0) {
			int sum = carry;
			if (i >= 0) {
				sum += firstStrValue.charAt(i--) - '0';
			}
			if (j >= 0) {
				sum += secondStrValue.charAt(j--) - '0';
			}
			carry = sum / 10;
			sb.append(sum % 10);
		}
		return sb.reverse().toString();
	}

	private static String multiplyStrings(String firstStrValue, String secondStrValue) {
		if (firstStrValue.equals("0") || secondStrValue.equals("0")) {
			return "0";
		}
		int firstValueLen = firstStrValue.length();
		int secondValueLen = secondStrValue.length();
		int[] result = new int[firstValueLen + secondValueLen];
		for (int i = firstValueLen - 1; i >= 0; i--) {
			for (int j = secondValueLen - 1; j >= 0; j--) {
				int p1 = i + j;
				int p2 = p1 + 1;
				int product = (firstStrValue.charAt(i) - '0') * (secondStrValue.charAt(j) - '0');
				int sum = product + result[p2];
				result[p2] = sum % 10;
				result[p1] += sum / 10;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int digit : result) {
			if (digit == 0 && sb.length() == 0) {
				continue;
			}
			sb.append(digit);
		}
		return sb.length() > 0 ? sb.toString() : "0";
	}

	/**
	 * Converts an unsigned integer value string to a binary string.
	 *
	 * @param input    the unsigned integer value string to convert
	 * @param options  an array of {@link IntOption} options (first is taken,
	 *                    if none is provided, {@link IntOption#BIG} is used)
	 * @return the binary string
	 * @throws ConverterException if the input unsigned integer value string is invalid
	 */
	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		validateInput(input, "^\\d+$");
		IntOption endian = getEndianFromOptions(options);

		StringBuilder binary = new StringBuilder();
		StringBuilder updatedInput = new StringBuilder(input);

		while (updatedInput.length() > 0) {
			StringBuilder semiResult = new StringBuilder();
			boolean foundNoneZero = false;
			int remainder = 0;

			for (int i = 0; i < updatedInput.length(); i++) {
				int digit = Character.getNumericValue(updatedInput.charAt(i));
				int dividend = remainder * 10 + digit;
				char charDigit = (char) ('0' + (dividend / 2));
				remainder = dividend % 2;

				if(!foundNoneZero && charDigit == '0'){
					continue;
				}
				foundNoneZero = true;
				semiResult.append(charDigit);
			}
			binary.append(remainder);
			updatedInput = semiResult;
		}

		String bitStr = binary.reverse().toString();
		bitStr = processBitString(bitStr, endian);

		return bitStr;
	}

	private String processBitString(String bitStr, IntOption endian) {
		String result = addMissingZerosToBitString(bitStr);
		if (LITTLE.equals(endian)) {
			result = changeEndian(result);
		}
		return result;
	}

	private String changeEndian(String bitStr) {
		StringBuilder builder = new StringBuilder();
		for (int i = bitStr.length() - 1; i >= 0; i -= BYTE_LENGTH) {
			int startIndex = Math.max(i - (BYTE_LENGTH - 1), 0);
			String byteStr = bitStr.substring(startIndex, i + 1);
			builder.append(byteStr);
		}
		return builder.toString();
	}

	private IntOption getEndianFromOptions(IOption[] options) throws ConverterException {
		try {
			return Objects.isNull(options) ||
					options.length == 0 ||
					options[0] == null ? BIG : (IntOption) options[0];
		} catch (ClassCastException e) {
			throw new ConverterException(String.format("IntConverter doesn't support option: %s", options[0]));
		}
	}
}
