package exceptions;

/**
 * This class serves as a custom exception. This exception is thrown when any problem during value conversion occurs.
 */
public class ConverterException extends Exception {

	public ConverterException(String message) {
		super(message);
	}

}
