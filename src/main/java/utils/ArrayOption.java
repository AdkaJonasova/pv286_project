package utils;


import java.util.Arrays;

public enum ArrayOption {
	ZEROX_PREFIXED_HEX_NUMBER("0x", "0x – Represent bytes as a 0x-prefixed hex number (e.g., 0xff; default)."),
	DECIMAL_NUMBER("0", "0 – Represent bytes as a decimal number (e.g., 255)."),
	CHARACTERS("a", "a – Represent bytes as characters (e.g., 'a', '\\x00')"),
	ZEROB_PREFIXED_BINARY_NUMBER("0b", "0b – Represent bytes as 0b-prefixed binary number (e.g., 0b11111111)."),
	LEFT_CURLY_BRACKETS("{", ""),
	RIGHT_CURLY_BRACKETS("}", ""),
	CURLY_BRACKETS("{}", "{ or } or {} – Use curly brackets in output (default)."),
	LEFT_SQUARE_BRACKETS("[", ""),
	RIGHT_SQUARE_BRACKETS("]", ""),
	SQUARE_BRACKETS("[]", "[ or ] or [] – Use square brackets in output.n"),
	LEFT_REGULAR_BRACKETS("(", ""),
	RIGHT_REGULAR_BRACKETS(")", ""),
	REGULAR_BRACKETS("()", "( or ) or () – Use regular brackets in output.\n");

	private final String text;
	private final String description;

	ArrayOption(String text, String description) {
		this.text = text;
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public String getDescription() {
		return description;
	}

	public static ArrayOption fromString(String text) {
		for (ArrayOption arrayOption : ArrayOption.values()) {
			if (arrayOption.getText().equalsIgnoreCase(text)) {
				return arrayOption;
			}
		}
		return null;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
