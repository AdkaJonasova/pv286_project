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
	private static final Map<String, Format> FORMAT_MAP = new HashMap<>();

	static {
		for (Format format : Format.values()) {
			FORMAT_MAP.put(format.getText(), format);
		}
	}

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
		return FORMAT_MAP.get(text.toLowerCase());
	}

	public static boolean contains(String value) {
		return Arrays.stream(values()).anyMatch(option -> option.getText().equals(value));
	}
}
