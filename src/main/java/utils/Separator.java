package utils;

public enum Separator {
	SPACE(" "),
	EMPTY(""),
	EQUAL("="),
	DASH("-");

	private final String text;

	Separator(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
