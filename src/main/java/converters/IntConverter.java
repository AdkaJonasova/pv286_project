package converters;

import exceptions.ConverterException;
import options.IOption;
import options.IntOption;

import java.util.Objects;

import static options.IntOption.BIG;
import static options.IntOption.LITTLE;

public class IntConverter extends Converter {
	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");
		IntOption endian = getEndianFromOptions(options);

		bitStr = processBitString(bitStr, endian);
		long unsignedInt = Long.parseUnsignedLong(bitStr, 2);

		return Long.toUnsignedString(unsignedInt);
	}

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
			bitStr = changeToLittleEndian(bitStr);
		}
		return bitStr;
	}

	private String changeToLittleEndian(String bitStr) {
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
		} catch (Exception e) {
			throw new ConverterException(String.format("IntConverter doesn't support option: %s", options[0]));
		}
	}
}
