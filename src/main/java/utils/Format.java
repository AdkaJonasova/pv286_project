package utils;

import converters.BytesConverter;
import converters.HexConverter;
import converters.IConverter;
import converters.IntConverter;

import java.util.Arrays;

public enum Format {
	INT("int", new IntConverter(), "int = Integer"),
	HEX("hex", new HexConverter(), "hex = Hex-encoded string"),
	BITS("bits", new IntConverter(), "bits = 0,1-represented bits"),
	BYTES("bytes", new BytesConverter(), "bytes = Raw bytes"),
	ARRAY("array", new BytesConverter(), "array = Byte array");

	private final String text;
	private final IConverter converter;
	private final String description;

	Format(String text, IConverter converter, String description) {
		this.text = text;
		this.converter = converter;
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public IConverter getConverter() {
		return converter;
	}

	public String getDescription() {
		return description;
	}

	public static Format fromString(String text) {
		for (Format format : Format.values()) {
			if (format.getText().equalsIgnoreCase(text)) {
				return format;
			}
		}
		return null;
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
