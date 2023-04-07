package converters;

import exceptions.ConverterException;
import options.HexOption;
import options.IOption;

import java.util.Objects;

import static options.HexOption.LONG;
import static options.HexOption.SHORT;

public class HexConverter extends Converter {

	@Override
	public String convertTo(String bitStr, IOption[] options) throws ConverterException {
		validateInput(bitStr, "^[01 ]+$");
		HexOption version = getVersionFromOptions(options);

		bitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < bitStr.length(); i += 4) {
			String nibble = bitStr.substring(i, i + 4);
			int value = Integer.parseInt(nibble, 2);
			builder.append(Integer.toHexString(value));
		}

		String result = builder.toString();
		return SHORT.equals(version) && result.startsWith("0") ? result.substring(1) : result;
	}

	@Override
	public String convertFrom(String input, IOption[] options) throws ConverterException {
		return this.convertFrom(input);
	}

	public String convertFrom(String input) throws ConverterException {
		validateInput(input, "^([0-9a-fA-F]{2}\\s?)+$");

		input = input.replace(" ", "");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(input.charAt(i), 16)))
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
