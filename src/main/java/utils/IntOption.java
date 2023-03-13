package utils;

public enum IntOption {
	BIG("big"),
	LITTLE("little");

	private final String text;

	IntOption(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
