package converters;

import options.BitsOption;
import options.IOption;

import static options.BitsOption.LEFT;

public abstract class Converter {
	public abstract String convertTo(String bitStr, IOption option);
	public abstract String convertFrom(String input, IOption option);

	protected static String addMissingZerosToBitString(String bitStr){
		return addMissingZerosToBitString(bitStr, LEFT);
	}
	protected static String addMissingZerosToBitString(String bitStr, BitsOption padSide) {
		int numOfMissingBits = bitStr.length() % 8;

		if (numOfMissingBits != 0){
			int padding = 8 - (numOfMissingBits);
			StringBuilder sb = new StringBuilder();
			if (LEFT.equals(padSide)){
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
}
