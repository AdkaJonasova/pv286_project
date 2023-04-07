package input;

import exceptions.InputParsingException;

/**
 * This interface provides methods for parsing program arguments.
 */
public interface IInputParser {

    /**
     * Parses given list of flags and values.
     *
     * @param input List of flags and their values.
     * @return instance of {@link ParserResult} with its attributes set to values from input.
     * @throws InputParsingException When any flag or value in the given input is not valid.
     */
    ParserResult parse(String[] input) throws InputParsingException;

}
