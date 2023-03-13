package utils;

import java.util.Arrays;

public enum BitsOption {
	LEFT("left"),
	RIGHT("right");

	private final String text;

	BitsOption(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
