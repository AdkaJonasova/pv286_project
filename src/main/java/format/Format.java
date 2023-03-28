package format;

import converters.*;

import java.util.Arrays;

public enum Format {
	INT("int", new IntConverter(), "int = Integer", "^(\\d)$"),
	HEX("hex", new HexConverter(), "hex = Hex-encoded string", "^0x([0-9a-fA-F]{2})$"),
	BITS("bits", new BitsConverter(), "bits = 0,1-represented bits", "^0b([01]{1,8})$"),
	BYTES("bytes", new BytesConverter(), "bytes = Raw bytes", "^'\\\\x([0-9a-fA-F]{2})'$"),
	ARRAY("array", new ArrayConverter(), "array = Byte array", "");

	private final String text;
	private final Converter converter;
	private final String description;
	private final String arrayRegex;


	Format(String text, Converter converter, String description, String arrayRegex) {
		this.text = text;
		this.converter = converter;
		this.description = description;
		this.arrayRegex = arrayRegex;;
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

	public String getArrayRegex() { return arrayRegex; }

	public static Format fromString(String text) {
		for (Format format : Format.values()) {
			if (format.getText().equalsIgnoreCase(text)) {
				return format;
			}
		}
		return null;
	}

	public static Format getFormatFromInputValue(String input){
		for (Format format : Format.values()) {
			if (input.matches(format.arrayRegex)) {
				return format;
			}
		}
		return null;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
