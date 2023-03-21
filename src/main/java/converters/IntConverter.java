package converters;

import options.IntOption;

import java.util.List;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

public class IntConverter extends Converter<IntOption> {
	@Override
	public String convertTo(String bitStr, List<IntOption> options) {
		IntOption endian = options == null || options.isEmpty() || options.get(0) == null ? BIG : options.get(options.size()-1);

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
	public String convertFrom(String input, List<IntOption> options) {
		IntOption endian = options == null || options.isEmpty() || options.get(0) == null ? BIG : options.get(options.size()-1);

		long unsignedInt = Long.parseUnsignedLong(input);
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
