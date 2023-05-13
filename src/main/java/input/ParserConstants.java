package input;

/**
 * This class contains constants which are used during {@link ArgumentParser#parse(String[])} method.
 */
public final class ParserConstants {

    private ParserConstants() {}

    /**
     * Specifies delimiter for attributes which are in --flagName=flagVal form.
     */
    public static final String LONG_ATTR_DELIMITER = "=";

    /**
     * Specifies characters which indicate start of the long version of flag.
     */
    public static final String LONG_FLAG_START = "--";


    /**
     * Specifies characters which indicate start of the short version of flag.
     */
    public static final String SHORT_FLAG_START = "-";

    public static final String ERROR_INVALID_FLAG_COMBINATION = "Invalid format and options combination.";

    public static final String ERROR_INVALID_TO_OPTIONS_LENGTH = "Invalid to options length.";
}
