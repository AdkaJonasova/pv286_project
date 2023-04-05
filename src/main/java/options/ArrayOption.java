package options;

import java.util.Arrays;

public enum ArrayOption implements IOption {
	ZEROX_PREFIXED_HEX_NUMBER("0x", "0x – Represent bytes as a 0x-prefixed hex number (e.g., 0xff; default)."),
	DECIMAL_NUMBER("0", "0 – Represent bytes as a decimal number (e.g., 255)."),
	CHARACTERS("a", "a – Represent bytes as characters (e.g., 'a', '\\x00')"),
	ZEROB_PREFIXED_BINARY_NUMBER("0b", "0b – Represent bytes as 0b-prefixed binary number (e.g., 0b11111111)."),
	CURLY_BRACKETS("{}", "{", "}", "{ or } or {} – Use curly brackets in output (default)."),
	SQUARE_BRACKETS("[]", "[", "]", "[ or ] or [] – Use square brackets in output.n"),
	REGULAR_BRACKETS("()", "(", ")", "( or ) or () – Use regular brackets in output.\n");

	private final String textV1;
	private final String textV2;
	private final String textV3;
	private final String description;

	ArrayOption(String text, String description) {
		this.textV1 = text;
		this.textV2 = "";
		this.textV3 = "";
		this.description = description;
	}

	ArrayOption(String textV1, String textV2, String textV3, String description) {
		this.textV1 = textV1;
		this.textV2 = textV2;
		this.textV3 = textV3;
		this.description = description;
	}

	@Override
	public String getText() {
		return textV1;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public String getOpen() {
		return textV2;
	}

	public String getClose() {
		return textV3;
	}

	public static ArrayOption fromString(String text) {
		for (ArrayOption arrayOption : ArrayOption.values()) {
			if (arrayOption.textV1.equalsIgnoreCase(text) ||
					arrayOption.textV2.equalsIgnoreCase(text) ||
					arrayOption.textV3.equalsIgnoreCase(text)) {
				return arrayOption;
			}
		}
		return null;
	}

	public static boolean isFromFirstSet(ArrayOption option) {
		return option.equals(DECIMAL_NUMBER) ||
				option.equals(ZEROB_PREFIXED_BINARY_NUMBER) ||
				option.equals(ZEROX_PREFIXED_HEX_NUMBER) ||
				option.equals(CHARACTERS);
	}

	public static boolean isFromSecondSet(ArrayOption option) {
		return option.equals(CURLY_BRACKETS) ||
				option.equals(SQUARE_BRACKETS) ||
				option.equals(REGULAR_BRACKETS);
	}


}
