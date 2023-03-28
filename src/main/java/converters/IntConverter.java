package converters;

import exceptions.ConverterException;
import options.IOption;
import options.IntOption;

import java.util.List;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

public class IntConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");

		IntOption endian = options == null || options.length == 0 || options[0] == null ? BIG : (IntOption) options[0];

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
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		validateInput(input, "^\\d+$");

		IntOption endian = options == null || options.length == 0 || options[0] == null ? BIG : (IntOption) options[0];

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
