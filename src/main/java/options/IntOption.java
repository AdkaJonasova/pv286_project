package options;

import java.util.Arrays;

public enum IntOption implements IOption {
	BIG("big",
			"Store the integer in big-endian representation (most significant byte at the lowest address) -> default"),
	LITTLE("little",
			"Store the integer in little-endian representation (least significant byte at the lowest address)");

	private final String text;
	private final String description;

	IntOption(String text, String description) {
		this.text = text;
		this.description = description;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getDescription() {
		return description;
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
