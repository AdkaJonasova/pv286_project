package utils;

import java.util.Arrays;

public enum BitsOption {
	LEFT("left", "left = If necessary, pad input with zero bits from left -> default"),
	RIGHT("right", "right = If necessary, pad input with zero bits from right");

	private final String text;
	private final String description;

	BitsOption(String text, String description) {
		this.text = text;
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public String getDescription() {
		return description;
	}

	public static BitsOption fromString(String text) {
		for (BitsOption bitsOption : BitsOption.values()) {
			if (bitsOption.getText().equalsIgnoreCase(text)) {
				return bitsOption;
			}
		}
		return null;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
