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
                inputParser = new InputParser();
                ParserResult parserResult = inputParser.parse(input);
                assertEquals(parserResult.getFrom().getText(), formats.get(0));
                assertEquals(parserResult.getTo().getText(), formats.get(1));
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

    @Test
    public void testInvalidArg() {
        String[] input = {"-ff", "array", "-t", "hex"};
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
            assertEquals(parserResult.getFrom().getText(), "bytes");
            assertEquals(parserResult.getTo().getText(), "hex");
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }
    //endregion

    //region correct options
    @Test
    public void testCorrectFromToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=big"};
        try {
            ParserResult parserResult = inputParser.parse(input);
            assertEquals(parserResult.getFrom().getText(), "bits");
            assertEquals(parserResult.getTo().getText(), "int");
            assertEquals(parserResult.getFromOption().getText(), "right");
            assertEquals(parserResult.getToOption().getText(), "big");
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    @Test
    public void testCorrectParseResultWhenPositionsOfArgsWithOptionsAreSwapped() {
        String[] input = {"-t", "int", "--to-options=big", "-f", "bits", "--from-options=right" };
        try {
            ParserResult parserResult = inputParser.parse(input);
            assertEquals(parserResult.getFrom().getText(), "bits");
            assertEquals(parserResult.getTo().getText(), "int");
            assertEquals(parserResult.getFromOption().getText(), "right");
            assertEquals(parserResult.getToOption().getText(), "big");
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }
    //endregion

    //region help arg
    @Test
    public void testParserResultContainsHelpArgWhenHGiven() {
        String[] input = {"-h"};
        try {
            ParserResult parserResult = inputParser.parse(input);
            assertTrue(parserResult.getShouldPrintHelp());
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

//    @Test
//    public void testParserResultContainsHelpArgWhenHelpGiven() {
//        String[] input = {"--help"};
//        try {
//            ParserResult parserResult = inputParser.parse(input);
//            assertTrue(parserResult.getShouldPrintHelp());
//        } catch (InputParsingException e) {
//            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
//            assert false;
//        }
//    }
    //endregion

    //region invalid options
    @Test
    public void testThrowingExceptionWhenFromBitsOptionsIsBig() {
        String[] input = {"-f", "bits", "--from-options=big", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenFromBitsOptionsIsLittle() {
        String[] input = {"-f", "bits", "--from-options=little", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenOptionsStartsWithOneDash() {
        String[] input = {"-f", "bits", "-from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenBitsToOptionsAreSet() {
        String[] input = {"-t", "bits", "--from-options=left", "-f", "int", "--to-options=left"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenBitsHaveTwoFromOptions() {
        String[] input = {"-f", "bits", "--from-options=left", "--from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenMissingFormatBeforeOptions() {
        String[] input = {"-f", "--from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }

    @Test
    public void testThrowingExceptionWhenArgsAreGivenInInvalidOrder() {
        String[] input = {"--from-options=left", "--to-options=big", "-f", "bits", "--from-options=left", "-t", "int"};
        assertThrows(InputParsingException.class, () -> inputParser.parse(input));
    }
    //endregion

    private List<List<String>> getFormatsVariations() {
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
