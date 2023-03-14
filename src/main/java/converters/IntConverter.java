package converters;

import utils.Format;
import utils.IntOption;
import utils.Separator;

import static utils.IntOption.LITTLE;

public class IntConverter implements IConverter {
	@Override
	public String convertTo(String bitStr, String option) {
		option = Separator.isEmpty(option) ? "big" : option;
		IntOption endian = IntOption.fromString(option);

		if (LITTLE.equals(endian)){
			StringBuilder builder = new StringBuilder();
			bitStr = addMissingZerosToBitString(bitStr);

			for (int i = bitStr.length() - 1; i >= 0; i -= 8) {
				int startIndex = Math.max(i - 7, 0);
				String chunk = bitStr.substring(startIndex, i + 1);
				builder.append(chunk);
			}
			bitStr = builder.toString();
		}

		long unsignedInt = Long.parseUnsignedLong(bitStr, 2);
		return Long.toUnsignedString(unsignedInt);
	}

	@Override
	public String convertFrom(String str, String option) {
		long unsignedInt = Long.parseUnsignedLong(str);
		String bitStr = Long.toBinaryString(unsignedInt);
		StringBuilder result = new StringBuilder();

		bitStr = addMissingZerosToBitString(bitStr);

		option = Separator.isEmpty(option) ? "big" : option;
		IntOption endian = IntOption.fromString(option);

		if (LITTLE.equals(endian)){
			for (int i = bitStr.length() - 1; i >= 0; i -= 8) {
				int startIndex = Math.max(i - 7, 0);
				String chunk = bitStr.substring(startIndex, i + 1);
				result.append(chunk);
			}
			return result.toString();
		}
		return bitStr;
	}

	private static String addMissingZerosToBitString(String bitStr) {
		if (bitStr.length() % 8 != 0){
			int padding = 8 - (bitStr.length() % 8);
			StringBuilder sb = new StringBuilder();
			sb.append("0".repeat(padding));
			sb.append(bitStr);
			bitStr = sb.toString();
		}
		return bitStr;
	}

}
