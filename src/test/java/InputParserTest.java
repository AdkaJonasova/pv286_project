import static org.junit.jupiter.api.Assertions.*;

import exception.InputParsingException;
import input.InputParser;
import input.ParserResult;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParserTest {

    @Test
    public void testArgsFromTo() {
        List<List<String>> variationsOfFormats = getFormatsVariations();
        for (List<String> formats : variationsOfFormats) {
            String[] input = {"-f", formats.get(0), "-t", formats.get(1)};
            checkParserResult(input, formats.get(0), formats.get(1));
        }
    }

    @Test
    public void testCorrectParserResultWhenArgsFromToAsText() {
        String[] input = {"--from=hex", "--to=int"};
        checkParserResult(input, "hex", "int");
    }

    @Test
    public void testCorrectParserResultWhenFromArgAsText() {
        String[] input = {"--from=hex", "-t", "bytes"};
        checkParserResult(input, "hex", "bytes");
    }

    @Test
    public void testCorrectParserResultWhenToArgAsText() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResult(input, "bytes", "hex");
    }

    @Test
    public void testCorrectParserResultWhenToArgAsTextAndOptionsGiven() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResult(input, "bytes", "hex");
    }

    @Test
    public void testThrowingExceptionWhenTwoSameFromArgsAreGiven() {
        String[] input = {"-f", "bytes", "--from=bytes" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenTwoSameToArgsAreGiven() {
        String[] input = {"-f", "bytes", "-t", "hex" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testCorrectParserResultWhenArgAsTextWithOptions() {
        String[] input = {"--from=bits", "--from-options=right", "--to=int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    public void testCorrectParserResultWhenArgAsTextWithOptionsAreSwapped() {
        String[] input = {"--to=int", "--to-options=big", "--from=bits", "--from-options=right"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }


    // region missing args and formats tests
    @Test
    public void testEmptyArgs() {
        String[] input = {};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingFromArgument() {
        String[] input = {"hex", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingToArgument() {
        String[] input = {"-f", "hex", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingFromFormat() {
        String[] input = {"-f", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingToFormat() {
        String[] input = {"-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingFromToArgs() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingFromToFormats() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingFromArgAndFormat() {
        String[] input = {"-f", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testMissingToArgAndFormat() {
        String[] input = {"-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    // endregion

    //region invalid args and formats tests
    @Test
    public void testInvalidFromArgMissingDash() {
        String[] input = {"f", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidFromArgMissingChar() {
        String[] input = {"-", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidToArgMissingDash() {
        String[] input = {"-f", "hex", "t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidToArgMissingChar() {
        String[] input = {"-f", "hex", "-", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidFromFormat() {
        String[] input = {"-f", "aray", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidToFormat() {
        String[] input = {"-f", "array", "-t", "hexe"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidArg() {
        String[] input = {"-ff", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenShortFromArgWithTwoDashes() {
        String[] input = {"--f", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenTextFromArgMissingDash() {
        String[] input = {"-from=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenTextFromArgIsInvalid() {
        String[] input = {"-f=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenFormatForTextFromArgIsInvalid() {
        String[] input = {"--from=aray", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenFormatForTextFromArgIsEmpty() {
        String[] input = {"--from=", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenEqualsMissing() {
        String[] input = {"--fromhex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region invalid args and formats positions tests
    @Test
    public void testInvalidPositionsArgsThenFormats() {
        String[] input = {"-f", "-t", "hex", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidPositionsFormatsThenArgs() {
        String[] input = {"hex", "hex", "-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testInvalidOrderOfArgsAndFormats() {
        String[] input = {"hex", "-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion o

    //region duplicate args and formats tests
    @Test
    public void testDuplicateFromArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-f", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testDuplicateToArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region swapped position from to args
    @Test
    public void testSwappedPositionToThenFromArg() {
        String[] input = {"-t", "hex", "-f", "bytes"};
        checkParserResult(input, "bytes", "hex");
    }
    //endregion

    //region correct options
    @Test
    public void testCorrectFromToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    public void testCorrectParseResultWhenPositionsOfArgsWithOptionsAreSwapped() {
        String[] input = {"-t", "int", "--to-options=big", "-f", "bits", "--from-options=right" };
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    public void testCorrectFromToDuplicateFromOptions() {
        String[] input = {"-f", "int", "--from-options=big", "--from-options=little", "-t", "int", "--to-options=little"};
        checkParserResultWithOptions(input, "int", "int", "little", "little");
    }

    @Test
    public void testCorrectFromToDuplicateToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=little", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }
    //endregion

    //region help arg
    @Test
    public void testParserResultContainsHelpArgWhenHGiven() {
        String[] input = {"-h"};
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertTrue(parserResult.getShouldPrintHelp());
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    @Test
    public void testParserResultContainsHelpArgWhenHelpGiven() {
        String[] input = {"--help"};
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertTrue(parserResult.getShouldPrintHelp());
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    @Test
    public void testThrowExceptionWhenHelpFirstWithOtherArgs() {
        String[] input = {"-h", "-f", "bits", "-to", "int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowExceptionWhenHelpLastWithOtherArgs() {
        String[] input = {"-f", "bits", "-t", "int", "-h"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region invalid options
    @Test
    public void testThrowingExceptionWhenFromBitsOptionsIsBig() {
        String[] input = {"-f", "bits", "--from-options=big", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenFromBitsOptionsIsLittle() {
        String[] input = {"-f", "bits", "--from-options=little", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenOptionsStartsWithOneDash() {
        String[] input = {"-f", "bits", "-from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenBitsToOptionsAreSet() {
        String[] input = {"-t", "bits", "--from-options=left", "-f", "int", "--to-options=left"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenMissingFormatBeforeOptions() {
        String[] input = {"-f", "--from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowingExceptionWhenArgsAreGivenInInvalidOrder() {
        String[] input = {"--from-options=left", "--to-options=big", "-f", "bits", "--from-options=left", "-t", "int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowExceptionWhenToOptionsInsteadOfFromOptions() {
        String[] input = {"-f", "int", "--to-options=little", "-t", "int", "--to-options=little"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    public void testThrowsExceptionWhenFromOptionsInsteadOfToOptions() {
        String[] input = {"-f", "int", "--from-options=little", "-t", "int", "--from-options=little"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
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

    private static void checkParserResult(String[] input, String from, String to) {
        try {
            InputParser inputParser = new InputParser();
            ParserResult parserResult = inputParser.parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    private static void checkParserResultWithOptions(String[] input, String from, String to, String fromOpt, String toOpt) {
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
            assertEquals(parserResult.getFromOption().getText(), fromOpt);
            assertEquals(parserResult.getToOption().getText(), toOpt);
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }
}
