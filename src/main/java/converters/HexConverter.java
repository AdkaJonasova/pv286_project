package converters;

import options.BitsOption;
import options.HexOption;
import options.IOption;

import java.util.List;

import static options.BitsOption.LEFT;

public class HexConverter extends Converter<HexOption> {

	@Override
	public String convertTo(String bitStr, List<HexOption> options) {
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
	public String convertFrom(String input, List<HexOption> options) {
		return this.convertFrom(input);
	}

	public String convertFrom(String input) {
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
