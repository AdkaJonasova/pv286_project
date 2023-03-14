package utils;


import converters.BytesConverter;
import converters.HexConverter;
import converters.IConverter;
import converters.IntConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Format {
	INT("int", new IntConverter()),
	HEX("hex", new HexConverter()),
	BITS("bits", new IntConverter()),
	BYTES("bytes", new BytesConverter()),
	ARRAY("array", new BytesConverter());

	private final String text;
	private final IConverter converter;

	Format(String text, IConverter converter) {
		this.text = text;
		this.converter = converter;
	}

	public String getText() {
		return text;
	}

	public IConverter getConverter() {
		return converter;
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
