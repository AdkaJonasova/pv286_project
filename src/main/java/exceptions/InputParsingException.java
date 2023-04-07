package exceptions;

/**
 * This class serves as a custom exception. This exception is thrown when any mistake in program arguments is spotted.
 */
public class InputParsingException extends Exception {

    public InputParsingException(String message) {
        super(message);
    }
}
