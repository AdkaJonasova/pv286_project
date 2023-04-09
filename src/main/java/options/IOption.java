package options;

/**
 * The IOption interface provides a template for supported options for corresponding converter
 * Subenums of IOption must implement the {@link #getText())} and {@link #getDescription())}
 * to provide option functionality.
 */
public interface IOption {
	/**
	 * Returns the text representation of the option.
	 * @return the string text of the option.
	 */
	String getText();

	/**
	 * Returns a brief description of what the option does.
	 * @return The description of the option.
	 */
	String getDescription();
}
