package input;

import exceptions.InputParsingException;

public interface IInputParser {

    /**
     * Parses given list of flags and values.
     *
     * @param input List of flags and their values.
     * @return <code>ParserResult</code> which has its attributes (from, to, option, delimiter, input, output) set.
     * @throws InputParsingException When any flag or value in the given input is not valid.
     */
    ParserResult parse(String[] input) throws InputParsingException;

}
