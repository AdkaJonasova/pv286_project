package format;

import converters.IntConverter;
import converters.HexConverter;
import converters.BitsConverter;
import converters.BytesConverter;
import converters.ArrayConverter;
import converters.Converter;

import java.util.Arrays;

/**
 * Enumeration of supported formats for conversion.
 * Each format holds a text form of format ,corresponding converter that can be used to conversion,
 * descriptions for help and regex for resoling format based on input value
 */
public enum Format {
	INT("int", new IntConverter(), "int = Integer", "^(?:1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])$"),
	HEX("hex", new HexConverter(), "hex = Hex-encoded string", "^0x([0-9a-fA-F]{2})$"),
	BITS("bits", new BitsConverter(), "bits = 0,1-represented bits", "^0b([01]{1,8})$"),
	BYTES("bytes", new BytesConverter(), "bytes = Raw bytes", "^(?:'\\\\x([0-9a-fA-F]{2})'|'(.)')$"),
	ARRAY("array", new ArrayConverter(), "array = Byte array", "");

	private final String text;
	private final Converter converter;
	private final String description;
	private final String arrayRegex;


	Format(String text, Converter converter, String description, String arrayRegex) {
		this.text = text;
		this.converter = converter;
		this.description = description;
		this.arrayRegex = arrayRegex;
	}

	public String getText() {
		return text;
	}

	public Converter getConverter() {
		return converter;
	}

	public String getDescription() {
		return description;
	}

	public String getArrayRegex() {
		return arrayRegex; }

	/**
	 * Gets the Format enum value corresponding to the given text representation.
	 *
	 * @param text the text representation of the format
	 * @return the Format enum value
	 */
	public static Format fromString(String text) {
		for (Format format : Format.values()) {
			if (format.getText().equalsIgnoreCase(text)) {
				return format;
			}
		}
		return null;
	}

	/**
	 * Gets the Format enum value based on input value that matches corresponding arrayRegex.
	 *
	 * @param input the input value to check for matching arrayRegex
	 * @return the Format enum value
	 */
	public static Format getFormatFromInputValue(String input){
		for (Format format : Format.values()) {
			if (input.matches(format.arrayRegex)) {
				return format;
			}
		}
		return null;
	}

	/**
	 * Checks whether the given text value is a supported Format.
	 *
	 * @param value the text value to check for support
	 * @return true if the given value is a supported Format, false otherwise
	 */
	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
