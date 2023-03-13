package utils;


public enum Format {
	INT("int"),
	HEX("hex"),
	BITS("bits"),
	BYTES("bytes"),
	ARRAY("array");

	private final String text;

	Format(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
