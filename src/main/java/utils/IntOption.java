package utils;

import java.util.Arrays;

public enum IntOption {
	BIG("big"),
	LITTLE("little");

	private final String text;

	IntOption(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
