import static org.junit.jupiter.api.Assertions.*;

import exceptions.InputParsingException;
import input.InputParser;
import input.ParserResult;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class InputParserTest {

    //region Basic tests
    @Test
    void testArgsFromTo() {
        List<List<String>> variationsOfFormats = getFormatsVariations();
        for (List<String> formats : variationsOfFormats) {
            String[] input = {"-f", formats.get(0), "-t", formats.get(1)};
            checkParserResultWithAllArgs(input, formats.get(0), formats.get(1), "", "", "");
        }
    }

    @Test
    void testCorrectParserResultWhenArgsFromToAsText() {
        String[] input = {"--from=hex", "--to=int"};
        checkParserResultWithAllArgs(input, "hex", "int", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenFromArgAsText() {
        String[] input = {"--from=hex", "-t", "bytes"};
        checkParserResultWithAllArgs(input, "hex", "bytes", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenToArgAsText() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResultWithAllArgs(input, "bytes", "hex", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenToArgAsTextAndOptionsGiven() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResultWithAllArgs(input, "bytes", "hex", "", "", "");
    }

    @Test
    void testThrowingExceptionWhenTwoSameFromArgsAreGiven() {
        String[] input = {"-f", "bytes", "--from=bytes" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTwoSameToArgsAreGiven() {
        String[] input = {"-f", "bytes", "-t", "hex" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testCorrectParserResultWhenArgAsTextWithOptions() {
        String[] input = {"--from=bits", "--from-options=right", "--to=int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    void testCorrectParserResultWhenArgAsTextWithOptionsAreSwapped() {
        String[] input = {"--to=int", "--to-options=big", "--from=bits", "--from-options=right"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    void testCorrectInputFile() {
        String[] input = {"-f", "bytes", "-t", "int", "-i", "my_file"};
        checkParserResultWithAllArgs(input, "bytes", "int", "my_file", "", "");
    }

    @Test
    void testCorrectOutputFile() {
        String[] input = {"-f", "bytes", "-t", "int", "-o", "my_file"};
        checkParserResultWithAllArgs(input, "bytes", "int", "", "my_file", "");
    }

    @Test
    void testCorrectDelimiter() {
        String[] input = {"-f", "bytes", "-t", "int", "-d", ","};
        checkParserResultWithAllArgs(input, "bytes", "int", "", "", ",");
    }
    // endregion

    // region missing args and formats tests
    @Test
    void testEmptyArgs() {
        String[] input = {};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingFromArgument() {
        String[] input = {"hex", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingToArgument() {
        String[] input = {"-f", "hex", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingFromFormat() {
        String[] input = {"-f", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingToFormat() {
        String[] input = {"-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingFromToArgs() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingFromToFormats() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingFromArgAndFormat() {
        String[] input = {"-f", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testMissingToArgAndFormat() {
        String[] input = {"-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    // endregion

    //region invalid args and formats tests
    @Test
    void testInvalidFromArgMissingDash() {
        String[] input = {"f", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidFromArgMissingChar() {
        String[] input = {"-", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidToArgMissingDash() {
        String[] input = {"-f", "hex", "t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidToArgMissingChar() {
        String[] input = {"-f", "hex", "-", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidFromFormat() {
        String[] input = {"-f", "aray", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidToFormat() {
        String[] input = {"-f", "array", "-t", "hexe"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidArg() {
        String[] input = {"-ff", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenShortFromArgWithTwoDashes() {
        String[] input = {"--f", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTextFromArgMissingDash() {
        String[] input = {"-from=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTextFromArgIsInvalid() {
        String[] input = {"-f=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFormatForTextFromArgIsInvalid() {
        String[] input = {"--from=aray", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFormatForTextFromArgIsEmpty() {
        String[] input = {"--from=", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenEqualsMissing() {
        String[] input = {"--fromhex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region invalid args and formats positions tests
    @Test
    void testInvalidPositionsArgsThenFormats() {
        String[] input = {"-f", "-t", "hex", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidPositionsFormatsThenArgs() {
        String[] input = {"hex", "hex", "-f", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testInvalidOrderOfArgsAndFormats() {
        String[] input = {"hex", "-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion o

    //region duplicate args and formats tests
    @Test
    void testDuplicateFromArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-f", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testDuplicateToArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testDuplicateInputFileArg() {
        String[] input = {"-f", "hex", "-t", "int", "-i", "my_file", "--input=my_file_2"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testDuplicateOutputFileArg() {
        String[] input = {"-f", "hex", "-t", "int", "-o", "my_file", "--output=my_file_2"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testDuplicateDelimiterArg() {
        String[] input = {"-f", "hex", "-t", "int", "-d", ",", "-d", "#"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region swapped position from to args
    @Test
    void testSwappedPositionToThenFromArg() {
        String[] input = {"-t", "hex", "-f", "bytes"};
        checkParserResultWithAllArgs(input, "bytes", "hex", "", "", "");
    }
    //endregion

    //region correct options
    @Test
    void testCorrectFromToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    void testCorrectParseResultWhenPositionsOfArgsWithOptionsAreSwapped() {
        String[] input = {"-t", "int", "--to-options=big", "-f", "bits", "--from-options=right" };
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    void testCorrectFromToDuplicateFromOptions() {
        String[] input = {"-f", "int", "--from-options=big", "--from-options=little", "-t", "int", "--to-options=little"};
        checkParserResultWithOptions(input, "int", "int", "little", "little");
    }

    @Test
    void testCorrectFromToDuplicateToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=little", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", "right", "big");
    }

    @Test
    void testCorrectArrayOptionsWithType1Option() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options=0x"};
        checkParserResultWithMultipleOptions(input, "bits", "array", Arrays.asList(null, null), Arrays.asList("0x", null));
    }

    @Test
    void testCorrectArrayOptionsWithType2Option() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options={}"};
        checkParserResultWithMultipleOptions(input, "bits", "array", Arrays.asList(null, null), Arrays.asList(null, "{}"));
    }

    @Test
    void testCorrectArrayOptionsWithBothOptionTypes() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options=0", "--to-options=()"};
        checkParserResultWithMultipleOptions(input, "bits", "array", Arrays.asList(null, null), Arrays.asList("0", "()"));
    }
    //endregion

    //region help arg
    @Test
    void testParserResultContainsHelpArgWhenHGiven() {
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
    void testParserResultContainsHelpArgWhenHelpGiven() {
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
    void testThrowExceptionWhenHelpFirstWithOtherArgs() {
        String[] input = {"-h", "-f", "bits", "-to", "int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowExceptionWhenHelpLastWithOtherArgs() {
        String[] input = {"-f", "bits", "-t", "int", "-h"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }
    //endregion

    //region invalid options
    @Test
    void testThrowingExceptionWhenFromBitsOptionsIsBig() {
        String[] input = {"-f", "bits", "--from-options=big", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFromBitsOptionsIsLittle() {
        String[] input = {"-f", "bits", "--from-options=little", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenOptionsStartsWithOneDash() {
        String[] input = {"-f", "bits", "-from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenBitsToOptionsAreSet() {
        String[] input = {"-t", "bits", "--to-options=left", "-f", "int", "--from-options=left"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenMissingFormatBeforeOptions() {
        String[] input = {"-f", "--from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenArgsAreGivenInInvalidOrder() {
        String[] input = {"--from-options=left", "--to-options=big", "-f", "bits", "--from-options=left", "-t", "int"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowExceptionWhenToOptionsInsteadOfFromOptions() {
        String[] input = {"-f", "int", "--to-options=little", "-t", "int", "--to-options=little"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenFromOptionsInsteadOfToOptions() {
        String[] input = {"-f", "int", "--from-options=little", "-t", "int", "--from-options=little"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenMissingFromOptionsAfterArg() {
        String[] input = {"-f", "bits", "--from-options=", "-t", "int", "--to-options=little"};
        assertThrows(InputParsingException.class, () -> new InputParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenMissingToOptionsAfterArg() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options="};
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

    private static void checkParserResultWithOptions(String[] input, String from, String to, String fromOpt, String toOpt) {
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
            assertEquals(parserResult.getFromOptions()[0].getText(), fromOpt);
            assertEquals(parserResult.getToOptions()[0].getText(), toOpt);
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    private static void checkParserResultWithMultipleOptions(String[] input, String from, String to,
                                                             List<String> fromOptions, List<String> toOptions) {
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
            for (int i = 0; i < parserResult.getFromOptions().length; i++) {
                if (Objects.isNull(fromOptions.get(i))) {
                    assertNull(parserResult.getFromOptions()[i]);
                } else {
                    assertEquals(parserResult.getFromOptions()[i].getText(), fromOptions.get(i));
                }
            }
            for (int i = 0; i < parserResult.getToOptions().length; i++) {
                if (Objects.isNull(toOptions.get(i))) {
                    assertNull(parserResult.getToOptions()[i]);
                } else {
                    assertEquals(parserResult.getToOptions()[i].getText(), toOptions.get(i));
                }
            }
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    private static void checkParserResultWithAllArgs(String[] input, String from, String to, String inputFile,
                                                     String outputFile, String delimiter) {
        try {
            ParserResult parserResult = new InputParser().parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
            assertEquals(parserResult.getInputFile(), inputFile);
            assertEquals(parserResult.getOutputFile(), outputFile);
            assertEquals(parserResult.getDelimiter(), delimiter);
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }
}
