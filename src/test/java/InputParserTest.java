import static org.junit.jupiter.api.Assertions.*;

import exception.InputParsingException;
import input.InputParser;
import input.ParserResult;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParserTest {

    InputParser inputParser = new InputParser();

    @Test
    public void testArgsFromTo() {
        List<List<String>> variationsOfFormats = getFormatsVariations();
        for (List<String> formats : variationsOfFormats) {
            String[] input = {"-f", formats.get(0), "-t", formats.get(1)};
            try {
                ParserResult parserResult = inputParser.parse(input);
                assertEquals(parserResult.getFrom(), formats.get(0));
                assertEquals(parserResult.getTo(), formats.get(1));
            } catch (InputParsingException e) {
                System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
                assert false;
            }
        }
    }
    // region missing args and formats tests
    @Test
    public void testEmptyArgs() {
        String[] input = {};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingFromArgument() {
        String[] input = {"hex", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingToArgument() {
        String[] input = {"-f", "hex", "bytes"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingFromFormat() {
        String[] input = {"-f", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingToFormat() {
        String[] input = {"-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingFromToArgs() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingFromToFormats() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingFromArgAndFormat() {
        String[] input = {"-f", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testMissingToArgAndFormat() {
        String[] input = {"-t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }
    // endregion

    //region invalid args and formats tests
    @Test
    public void testInvalidFromArgMissingDash() {
        String[] input = {"f", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidFromArgMissingChar() {
        String[] input = {"-", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidToArgMissingDash() {
        String[] input = {"-f", "hex", "t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidToArgMissingChar() {
        String[] input = {"-f", "hex", "-", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidFromFormat() {
        String[] input = {"-f", "aray", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidToFormat() {
        String[] input = {"-f", "array", "-t", "hexe"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }
    //endregion

    //region invalid args and formats positions tests
    @Test
    public void testInvalidPositionsArgsThenFormats() {
        String[] input = {"-f", "-t", "hex", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidPositionsFormatsThenArgs() {
        String[] input = {"hex", "hex", "-f", "-t"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testInvalidOrderOfArgsAndFormats() {
        String[] input = {"hex", "-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }
    //endregion o

    //region duplicate args and formats tests
    @Test
    public void testDuplicateFromArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-f", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testDuplicateToArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }
    //endregion

    //region swapped position from to args
    @Test
    public void testSwappedPositionToThenFromArg() {
        String[] input = {"-t", "hex", "-f", "bytes"};
        try {
            ParserResult parserResult = inputParser.parse(input);
            assertEquals(parserResult.getFrom(), "bytes");
            assertEquals(parserResult.getTo(), "hex");
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }
    //endregion

    private static List<List<String>> getFormatsVariations() {
        List<String> list = Arrays.asList("bytes", "hex", "int", "bits", "array");
        List<List<String>> variationsOfFormats = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                List<String> variation = Arrays.asList(list.get(i), list.get(j));
                variationsOfFormats.add(variation);
            }
        }
        return variationsOfFormats;
    }
}
