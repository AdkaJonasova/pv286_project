import static org.junit.jupiter.api.Assertions.*;

import exceptions.InputParsingException;
import input.ArgumentParser;
import input.ParserResult;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class ArgumentParserTest {

    //region Basic tests
    @Test
    void testArgsFromTo() {
        List<List<String>> variationsOfFormats = getFormatsVariations();
        for (List<String> formats : variationsOfFormats) {
            String[] input = {"-f", formats.get(0), "-t", formats.get(1)};
            checkParserResult(input, formats.get(0), formats.get(1), "", "", "");
        }
    }

    @Test
    void testCorrectParserResultWhenArgsFromToAsText() {
        String[] input = {"--from=hex", "--to=int"};
        checkParserResult(input, "hex", "int", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenFromArgAsText() {
        String[] input = {"--from=hex", "-t", "bytes"};
        checkParserResult(input, "hex", "bytes", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenToArgAsText() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResult(input, "bytes", "hex", "", "", "");
    }

    @Test
    void testCorrectParserResultWhenToArgAsTextAndOptionsGiven() {
        String[] input = {"-f", "bytes", "--to=hex"};
        checkParserResult(input, "bytes", "hex", "", "", "");
    }

    @Test
    void testThrowingExceptionWhenTwoSameFromArgsAreGiven() {
        String[] input = {"-f", "bytes", "--from=bytes" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTwoSameToArgsAreGiven() {
        String[] input = {"-f", "bytes", "-t", "hex" ,"--to=hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testCorrectParserResultWhenArgAsTextWithOptions() {
        String[] input = {"--from=bits", "--from-options=right", "--to=int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, "");
    }

    @Test
    void testCorrectParserResultWhenArgAsTextWithOptionsAreSwapped() {
        String[] input = {"--to=int", "--to-options=big", "--from=bits", "--from-options=right"};
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, "");
    }

    @Test
    void testCorrectInputFile() {
        String[] input = {"-f", "bytes", "-t", "int", "-i", "my_file"};
        checkParserResult(input, "bytes", "int", "my_file", "", "");
    }

    @Test
    void testCorrectOutputFile() {
        String[] input = {"-f", "bytes", "-t", "int", "-o", "my_file"};
        checkParserResult(input, "bytes", "int", "", "my_file", "");
    }

    @Test
    void testCorrectDelimiter() {
        String[] input = {"-f", "bytes", "-t", "int", "-d", ","};
        checkParserResult(input, "bytes", "int", "", "", ",");
    }

    @Test
    void testCorrectDelimiterLongFormat() {
        String[] input = {"-f", "bytes", "-t", "int", "--delimiter=;'!?"};
        checkParserResult(input, "bytes", "int", "", "", ";'!?");
    }

    @Test
    void testCorrectDelimiterTwoChars() {
        String[] input = {"-f", "bytes", "-t", "int", "-d", ",,"};
        checkParserResult(input, "bytes", "int", "", "", ",,");
    }

    @Test
    void testCorrectLongerDelimiterTenChars() {
        String[] input = {"-f", "bytes", "-t", "int", "-d", ",.,78;8*-!"};
        checkParserResult(input, "bytes", "int", "", "", ",.,78;8*-!");
    }
    // endregion

    // region Missing args and formats tests
    @Test
    void testEmptyArgs() {
        String[] input = {};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingFromArgument() {
        String[] input = {"hex", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingToArgument() {
        String[] input = {"-f", "hex", "bytes"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingFromFormat() {
        String[] input = {"-f", "-t", "bytes"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingToFormat() {
        String[] input = {"-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingFromToArgs() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingFromToFormats() {
        String[] input = {"-f", "-t"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingFromArgAndFormat() {
        String[] input = {"-f", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingToArgAndFormat() {
        String[] input = {"-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    // endregion

    //region Invalid args and formats tests
    @Test
    void testInvalidFromArgMissingDash() {
        String[] input = {"f", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidFromArgMissingChar() {
        String[] input = {"-", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidToArgMissingDash() {
        String[] input = {"-f", "hex", "t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidToArgMissingChar() {
        String[] input = {"-f", "hex", "-", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidFromFormat() {
        String[] input = {"-f", "aray", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidToFormat() {
        String[] input = {"-f", "array", "-t", "hexe"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidArg() {
        String[] input = {"-ff", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenShortFromArgWithTwoDashes() {
        String[] input = {"--f", "array", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTextFromArgMissingDash() {
        String[] input = {"-from=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenTextFromArgIsInvalid() {
        String[] input = {"-f=hex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFormatForTextFromArgIsInvalid() {
        String[] input = {"--from=aray", "--to=int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFormatForTextFromArgIsEmpty() {
        String[] input = {"--from=", "--to=int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenEqualsMissing() {
        String[] input = {"--fromhex", "--to=int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenDelimiterWitFromArray() {
        String[] input = {"-f", "array", "-t", "int", "-d", ":"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    //endregion

    //region Invalid args and formats positions tests
    @Test
    void testInvalidPositionsArgsThenFormats() {
        String[] input = {"-f", "-t", "hex", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidPositionsFormatsThenArgs() {
        String[] input = {"hex", "hex", "-f", "-t"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testInvalidOrderOfArgsAndFormats() {
        String[] input = {"hex", "-f", "hex", "-t"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    //endregion o

    //region Invalid delimiter tests
    @Test
    void testMissingToAndFromWithDelimiter() {
        String[] input = {"-d"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingToAndFromWithDelimiterAndRecord() {
        String[] input = {"-d", ","};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testFromArgMissingDelimiter() {
        String[] input = {"hex", "-t", "array", ","};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testMissingDelimiter() {
        String[] input = {"-f", "hex", "-t", "int", ",."};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    //endregion

    //region Duplicate args and formats tests
    @Test
    void testDuplicateFromArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-f", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testDuplicateToArg() {
        String[] input = {"-f", "hex", "-t", "hex", "-t", "hex"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testDuplicateInputFileArg() {
        String[] input = {"-f", "hex", "-t", "int", "-i", "my_file", "--input=my_file_2"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testDuplicateOutputFileArg() {
        String[] input = {"-f", "hex", "-t", "int", "-o", "my_file", "--output=my_file_2"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testDuplicateDelimiterArg() {
        String[] input = {"-f", "hex", "-t", "int", "-d", ",", "-d", "#"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    //endregion

    //region Swapped position from to args tests
    @Test
    void testSwappedPositionToThenFromArg() {
        String[] input = {"-t", "hex", "-f", "bytes"};
        checkParserResult(input, "bytes", "hex", "", "", "");
    }
    //endregion

    //region Correct options tests
    @Test
    void testCorrectFromToOptions() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=big"};
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, "");
    }

    @Test
    void testCorrectFromToOptionsAndDelimiter() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=big", "-d", ","};
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, ",");
    }

    @Test
    void testCorrectParseResultWhenPositionsOfArgsWithOptionsAreSwapped() {
        String[] input = {"-t", "int", "--to-options=big", "-f", "bits", "--from-options=right" };
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, "");
    }

    @Test
    void testCorrectFromToDuplicateFromOptions() {
        String[] input = {"-f", "int", "--from-options=big", "--from-options=little", "-t", "int", "--to-options=little"};
        checkParserResultWithOptions(input, "int", "int", new String[] { "little" }, new String[] { "little", null }, "");
    }

    @Test
    void testCorrectFromToDuplicateToOptionsAndLongDelimiter() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options=little", "--to-options=big", "--delimiter=;'!?"};
        checkParserResultWithOptions(input, "bits", "int", new String[] { "right" }, new String[] { "big", null }, ";'!?");
    }

    @Test
    void testCorrectArrayOptionsWithType1Option() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options=0x"};
        checkParserResultWithOptions(input, "bits", "array", new String[] { null }, new String[] { "0x", null }, "");
    }

    @Test
    void testCorrectArrayOptionsWithType2Option() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options={}"};
        checkParserResultWithOptions(input, "bits", "array", new String[] { null }, new String[] { null, "{}" }, "");
    }

    @Test
    void testCorrectArrayOptionsWithBothOptionTypes() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options=0", "--to-options=()"};
        checkParserResultWithOptions(input, "bits", "array", new String[] { null }, new String[] { "0", "()" }, "");
    }

    @Test
    void testCorrectArrayOptionsWithType1OptionsWithDuplicate() {
        String[] input = {"-f", "bits", "-t", "array", "--to-options=0", "--to-options=0x"};
        checkParserResultWithOptions(input, "bits", "array", new String[] { null }, new String[] { "0x", null }, "");
    }
    //endregion

    //region Help arg tests
    @Test
    void testParserResultContainsHelpArgWhenHGiven() {
        String[] input = {"-h"};
        try {
            ParserResult parserResult = new ArgumentParser().parse(input);
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
            ParserResult parserResult = new ArgumentParser().parse(input);
            assertTrue(parserResult.getShouldPrintHelp());
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    @Test
    void testThrowExceptionWhenHelpFirstWithOtherArgs() {
        String[] input = {"-h", "-f", "bits", "-to", "int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowExceptionWhenHelpLastWithOtherArgs() {
        String[] input = {"-f", "bits", "-t", "int", "-h"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }
    //endregion

    //region Invalid options tests
    @Test
    void testThrowingExceptionWhenFromBitsOptionsIsBig() {
        String[] input = {"-f", "bits", "--from-options=big", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenFromBitsOptionsIsLittle() {
        String[] input = {"-f", "bits", "--from-options=little", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenOptionsStartsWithOneDash() {
        String[] input = {"-f", "bits", "-from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenBitsToOptionsAreSet() {
        String[] input = {"-t", "bits", "--to-options=left", "-f", "int", "--from-options=left"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenMissingFormatBeforeOptions() {
        String[] input = {"-f", "--from-options=left", "-t", "int", "--to-options=big"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowingExceptionWhenArgsAreGivenInInvalidOrder() {
        String[] input = {"--from-options=left", "--to-options=big", "-f", "bits", "--from-options=left", "-t", "int"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowExceptionWhenToOptionsInsteadOfFromOptions() {
        String[] input = {"-f", "int", "--to-options=little", "-t", "int", "--to-options=little"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenFromOptionsInsteadOfToOptions() {
        String[] input = {"-f", "int", "--from-options=little", "-t", "int", "--from-options=little"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenMissingFromOptionsAfterArg() {
        String[] input = {"-f", "bits", "--from-options=", "-t", "int", "--to-options=little"};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
    }

    @Test
    void testThrowsExceptionWhenMissingToOptionsAfterArg() {
        String[] input = {"-f", "bits", "--from-options=right", "-t", "int", "--to-options="};
        assertThrows(InputParsingException.class, () -> new ArgumentParser().parse(input));
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

    private static void checkParserResultWithOptions(String[] input, String from, String to, String[] fromOptions,
                                                     String[] toOptions, String delimiter) {
        try {
            ParserResult parserResult = new ArgumentParser().parse(input);
            assertEquals(parserResult.getFrom().getText(), from);
            assertEquals(parserResult.getTo().getText(), to);
            assertEquals(parserResult.getDelimiter(), delimiter);
            for (int i = 0; i < parserResult.getFromOptions().length; i++) {
                if (Objects.isNull(fromOptions[i])) {
                    assertNull(parserResult.getFromOptions()[i]);
                } else {
                    assertEquals(parserResult.getFromOptions()[i].getText(), fromOptions[i]);
                }
            }
            for (int i = 0; i < parserResult.getToOptions().length; i++) {
                if (Objects.isNull(toOptions[i])) {
                    assertNull(parserResult.getToOptions()[i]);
                } else {
                    assertEquals(parserResult.getToOptions()[i].getText(), toOptions[i]);
                }
            }
        } catch (InputParsingException e) {
            System.out.printf("Parsing failed on input: %s%n", Arrays.toString(input));
            assert false;
        }
    }

    private static void checkParserResult(String[] input, String from, String to, String inputFile,
                                          String outputFile, String delimiter) {
        try {
            ParserResult parserResult = new ArgumentParser().parse(input);
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
