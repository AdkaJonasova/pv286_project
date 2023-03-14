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

	public static IntOption fromString(String text) {
		for (IntOption intOption : IntOption.values()) {
			if (intOption.getText().equalsIgnoreCase(text)) {
				return intOption;
			}
		}
		return null;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
