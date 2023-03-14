package converters;

import static utils.Separator.EMPTY;
import static utils.Separator.SPACE;

public class HexConverter implements IConverter {
	@Override
	public String convertTo(String bitStr, String option) {
		return this.convertTo(bitStr);
	}

	public String convertTo(String bitStr) {
		StringBuilder builder = new StringBuilder();

		StringBuilder bitStrBuilder = new StringBuilder(bitStr);
		while (bitStrBuilder.length() % 4 != 0) {
			bitStrBuilder.insert(0, "0");
		}
		bitStr = bitStrBuilder.toString();

		for (int i = 0; i < bitStr.length(); i += 4) {
			String nibble = bitStr.substring(i, i + 4);
			int value = Integer.parseInt(nibble, 2);
			builder.append(Integer.toHexString(value));
		}

		return builder.toString();
	}

	@Override
	public String convertFrom(String str, String option) {
		return this.convertTo(str);
	}

	public String convertFrom(String str) {
		str = str.replace(SPACE.getText(), EMPTY.getText());
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String binaryString = String.format("%4s", Integer.toBinaryString(Character.digit(str.charAt(i), 16)))
					.replace(' ', '0');
			builder.append(binaryString);
		}
		return builder.toString();
	}
}
