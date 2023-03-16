package converters;

import options.IOption;

import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class HexConverter extends Converter<IOption> {
	@Override
	public String convertTo(String bitStr, IOption option) {
		return this.convertTo(bitStr);
	}

	public String convertTo(String bitStr) {
		bitStr = addMissingZerosToBitString(bitStr);

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < bitStr.length(); i += 4) {
			String nibble = bitStr.substring(i, i + 4);
			int value = Integer.parseInt(nibble, 2);
			builder.append(Integer.toHexString(value));
		}

		return builder.toString();
	}

	@Override
	public String convertFrom(String input, IOption option) {
		return this.convertFrom(input);
	}

	public String convertFrom(String input) {
		input = input.replace(SPACE.getText(), EMPTY.getText());
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(input.charAt(i), 16)))
					.replace(' ', '0');
			builder.append(binaryString);
		}
		return builder.toString();
	}
}
