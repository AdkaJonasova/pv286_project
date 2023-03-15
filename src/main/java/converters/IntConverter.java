package converters;

import options.IOption;
import options.IntOption;
import utils.Separator;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

public class IntConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption option) {
		IntOption endian = option == null ? BIG : (IntOption) option;

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
	public String convertFrom(String str, IOption option) {
		IntOption endian = option == null ? BIG : (IntOption) option;

		long unsignedInt = Long.parseUnsignedLong(str);
		String bitStr = Long.toBinaryString(unsignedInt);
		StringBuilder result = new StringBuilder();

		bitStr = addMissingZerosToBitString(bitStr);

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
}
