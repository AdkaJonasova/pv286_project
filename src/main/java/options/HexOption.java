package options;

/**
 * Enumeration of supported options for {@link converters.HexConverter} class.
 * It implements the {@link IOption} interface.
 */
public enum HexOption implements IOption{
	SHORT("short", "Omitted first 0 if presented"),
	LONG("long", "Beginning 0 is presented if contains");

	private final String text;
	private final String description;

	HexOption(String text, String description) {
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
}
