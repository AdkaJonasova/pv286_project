package utils;


import java.util.Arrays;

public enum Format {
	INT("int"),
	HEX("hex"),
	BITS("bits"),
	BYTES("bytes"),
	ARRAY("array");

	private final String text;

	Format(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
