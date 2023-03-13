package utils;

public enum BitsOption {
	LEFT("left"),
	RIGHT("right");

	private final String text;

	BitsOption(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
