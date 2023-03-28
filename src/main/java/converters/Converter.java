package converters;

import exceptions.ConverterException;
import options.BitsOption;
import options.IOption;

import java.util.List;

import static options.BitsOption.LEFT;

public abstract class Converter {
	protected static String addMissingZerosToBitString(String bitStr) {
		return addMissingZerosToBitString(bitStr, LEFT);
	}

	protected static String addMissingZerosToBitString(String bitStr, BitsOption padSide) {
		int numOfMissingBits = bitStr.length() % 8;

		if (numOfMissingBits != 0) {
			int padding = 8 - (numOfMissingBits);
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

	protected static boolean isNotValidInput(String input, String regex) {
		return !input.matches(regex);
	}

	public abstract String convertTo(String bitStr, List<IOption> options) throws ConverterException;

	public abstract String convertFrom(String input, List<IOption> options) throws ConverterException;
}
