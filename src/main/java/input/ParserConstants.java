package input;

/**
 * This class contains constants which are used during {@link InputParser#parse(String[])} method.
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

}
