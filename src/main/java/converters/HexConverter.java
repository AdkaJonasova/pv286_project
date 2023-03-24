package converters;

import exceptions.ConverterException;
import options.HexOption;

import java.util.List;


public class HexConverter extends Converter<HexOption> {

	@Override
	public String convertTo(String bitStr, List<HexOption> options) throws ConverterException {
		if(!validateInput(bitStr, "^[0-1 ]+$")){
			throw new ConverterException(String.format("Invalid input format: %s", bitStr));
		}

		HexOption version = options == null || options.isEmpty() || options.get(0) == null ? HexOption.LONG : options.get(options.size() -1 );

		bitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < bitStr.length(); i += 4) {
			String nibble = bitStr.substring(i, i + 4);
			int value = Integer.parseInt(nibble, 2);
			builder.append(Integer.toHexString(value));
		}

		String result = builder.toString();
		return HexOption.SHORT.equals(version) && result.startsWith("0") ? result.substring(1) : result;
	}

	@Override
	public String convertFrom(String input, List<HexOption> options) throws ConverterException {
		return this.convertFrom(input);
	}

	public String convertFrom(String input) throws ConverterException {
		if(!validateInput(input, "^([0-9a-fA-F]{2}\\s?)+$")){
			throw new ConverterException(String.format("Invalid input format: %s", input));
		}

		input = input.replace(" ", "");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(input.charAt(i), 16)))
					.replace(' ', '0');
			builder.append(binaryString);
		}
		return builder.toString();
	}
}
