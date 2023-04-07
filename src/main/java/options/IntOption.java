package options;

import java.util.Arrays;

/**
 * Enumeration of supported options for {@link converters.IntConverter} class.
 * It implements the {@link IOption} interface.
 */
public enum IntOption implements IOption {
	BIG("big",
			"Store the integer in big-endian representation " +
					"(most significant byte at the lowest address) -> default"),
	LITTLE("little",
			"Store the integer in little-endian representation " +
					"(least significant byte at the lowest address)");

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

	/**
	 * Gets the IntOption enum value corresponding to the given text representation.
	 *
	 * @param text the text representation of the option
	 * @return the IntOption enum value
	 */
	public static IntOption fromString(String text) {
		for (IntOption intOption : IntOption.values()) {
			if (intOption.getText().equalsIgnoreCase(text)) {
				return intOption;
			}
		}
		return null;
	}

	/**
	 * Checks whether the given text value is a supported IntOption.
	 *
	 * @param value the text value to check for support
	 * @return true if the given value is a supported IntOption, false otherwise
	 */
	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
