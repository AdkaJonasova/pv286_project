package options;

import java.util.Arrays;

public enum BitsOption implements IOption {
	LEFT("left", "left = If necessary, pad input with zero bits from left -> default", false),
	RIGHT("right", "right = If necessary, pad input with zero bits from right", false),
	SHORT("short", "Omitted 0 bits from the left", true);

	private final String text;
	private final String description;

	private final Boolean isPrivate;

	BitsOption(String text, String description, boolean isPrivate) {
		this.text = text;
		this.description = description;
		this.isPrivate = isPrivate;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
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
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value) && !option.isPrivate);
	}
}
