import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PanbyteTest {

    //region Provided tests
    @Test
    void testFromHexToBytes() {
        String echo = "74657374";
        String[] args = {"-f", "hex", "-t", "bytes"};
        String expectedOutput = "test";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @FuzzTest
    void fuzzTestIntToHex(int input) {
        if (input > 50) {
            System.out.println(input);
            String[] args2 = {"-f", "bytes", "-t", "int"};
            String[] args1 = {"-f", "int", "-t", "bytes"};
            String fromBytesToInt = getOutputOfProgramCall(String.valueOf(input), args1);
            String inputResult = getOutputOfProgramCall(fromBytesToInt, args2);
            assertEquals(String.valueOf(input), inputResult);
        }
    }

    @Test
    void testFromBytesToHex() {
        String echo = "test";
        String[] args = {"-f", "bytes", "-t", "hex"};
        String expectedOutput = "74657374";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromSpacedHexToBytes() {
        String echo = "74 65 73 74";
        String[] args = {"-f", "hex", "-t", "bytes"};
        String expectedOutput = "test";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromIntToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "-t", "hex"};
        String expectedOutput = "499602d2";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromIntOptionsBigToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "--from-options=big", "-t", "hex"};
        String expectedOutput = "499602d2";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromIntOptionsLittleToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "--from-options=little", "-t", "hex"};
        String expectedOutput = "d2029649";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromHexToInt() {
        String echo = "499602d2";
        String[] args = {"-f", "hex", "-t", "int"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromHexToIntOptionsBig() {
        String echo = "499602d2";
        String[] args = {"-f", "hex", "-t", "int", "--to-options=big"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromHexToIntOptionsLittle() {
        String echo = "d2029649";
        String[] args = {"-f", "hex", "-t", "int", "--to-options=little"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromBitsToBytes() {
        String echo = "100 1111 0100 1011";
        String[] args = {"-f", "bits", "-t", "bytes"};
        String expectedOutput = "OK";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromBitsOptionsLeftToBytes() {
        String echo = "100111101001011";
        String[] args = {"-f", "bits", "--from-options=left", "-t", "bytes"};
        String expectedOutput = "OK";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromBitsOptionsRightToHex() {
        String echo = "100111101001011";
        String[] args = {"-f", "bits", "--from-options=right", "-t", "hex"};
        String expectedOutput = "9e96";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromBytesToBits() {
        String echo = "OK";
        String[] args = {"-f", "bytes", "-t", "bits"};
        String expectedOutput = "0100111101001011";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromHexToArray() {
        String echo = "01020304";
        String[] args = {"-f", "hex", "-t", "array"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToHex() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "hex"};
        String expectedOutput = "01020304";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArray() {
        String echo = "{0x01,2,0b11 ,'\\x04' }";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsHexRepresentation() {
        String echo = "[0x01, 2, 0b11, '\\x04']";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0x"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsDecimalRepresentation() {
        String echo = "(0x01, 2, 0b11, '\\x04')";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0"};
        String expectedOutput = "{1, 2, 3, 4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsCharRepresentation() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "array", "--to-options=a"};
        String expectedOutput = "{'\\x01', '\\x02', '\\x03', '\\x04'}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsBinaryRepresentation() {
        String echo = "[0x01, 2, 0b11, '\\x04']";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0b"};
        String expectedOutput = "{0b1, 0b10, 0b11, 0b100}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsRegularBrackets() {
        String echo = "(0x01, 2, 0b11, '\\x04')";
        String[] args = {"-f", "array", "-t", "array", "--to-options=("};
        String expectedOutput = "(0x1, 0x2, 0x3, 0x4)";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsDecRepSquareBracket() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0", "--to-options=["};
        String expectedOutput = "[1, 2, 3, 4]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNestedFromArrayToArray() {
        String echo = "[[1, 2], [3, 4], [5, 6]]";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNestedFromArrayToArrayOptionsCurlyBracketsDecimalRepresentation() {
        String echo = "[[1, 2], [3, 4], [5, 6]]";
        String[] args = {"-f", "array", "-t", "array", "--to-options={", "--to-options=0"};
        String expectedOutput = "{{1, 2}, {3, 4}, {5, 6}}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNestedFromArrayToArrayOptionsDecimalRepresentationSquareBrackets() {
        String echo = "{{0x01, (2), [3, 0b100, 0x05], '\\x06'}}";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0", "--to-options=["};
        String expectedOutput = "[[1, [2], [3, 4, 5], 6]]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNestedFromArrayToArrayEmptyBrackets() {
        String echo = "()";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNestedFromArrayToArrayOptionsSquareBrackets() {
        String echo = "([],{})";
        String[] args = {"-f", "array", "-t", "array", "--to-options=["};
        String expectedOutput = "[[], []]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    // endregion

    //region Delimiter tests
    @Test
    void testFromBytesToHexWithDelimiter() {
        String echo = "macka,lietadlo,ohnos,pes,krodil,semafor,ahah";
        String[] args = {"-f", "bytes", "-t", "int", "-d", ","};
        String expectedOutput = "469785340769,7811886579175418991,478493437811,7366003,118139239295340,32481142781669234,1634230632";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromSpacedHexToBytesWithDelimiter() {
        String echo = "74 65 73 74,.;'4f 4b,.;'5375506552";
        String[] args = {"-f", "hex", "-d", ",.;'", "-t", "bytes"};
        String expectedOutput = "test,.;'OK,.;'SuPeR";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromArrayToArrayOptionsRegularBracketsWithDelimiter() {
        String echo = "16909060qw7.;8875213328";
        String[] args = {"-f", "int", "--delimiter=qw7.;", "-t", "array", "--to-options=("};
        String expectedOutput = "(0x1, 0x2, 0x3, 0x4)qw7.;(0x2, 0x11, 0x1, 0x2, 0x10)";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromBitsToBytesWithDefaultDelimiter() {
        String echo = "100 1111 0100 1011" + System.lineSeparator() +
                "1110000 01100101 01110011" + System.lineSeparator() +
                "01101101011000010110110101100001" + System.lineSeparator() +
                "0 1 1 0 1 1 0 1 01101111 01110010 011 00101";
        String[] args = {"-f", "bits","-t", "bytes"};
        String expectedOutput = "OK" + System.lineSeparator() +
                "pes" + System.lineSeparator() +
                "mama" + System.lineSeparator() +
                "more";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }
    //endregion

    //region File test
//    @Test
//    void testFromFileWithWindowsDelimiterToStdout() {
//        URL url = getClass().getClassLoader().getResource("inputFileWithWindowsDelimiter.txt");
//
//        String[] args = {"-f", "hex", "-t", "bytes", "-i", url.getPath()};
//        String expectedOutput = "test\r\nmacka\r\npes";
//        String actualOutput = getOutputOfProgramCall(args);
//
//    }

    //endregion
    private String getOutputOfProgramCall(String echo, String[] args) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(echo.getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8)
        ) {
            System.setIn(byteArrayInputStream);
            System.setOut(printStream);
            PanByte.main(args);
            String[] outputLines = byteArrayOutputStream.toString(StandardCharsets.UTF_8).split(System.lineSeparator());
            if (outputLines.length > 1) {
                return String.join(System.lineSeparator(), outputLines);
            }
            return outputLines[outputLines.length - 1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    private String getOutputOfProgramCall(String[] args) {
//        try (
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8)
//        ) {
//            System.setOut(printStream);
//            PanByte.main(args);
//            String[] outputLines = byteArrayOutputStream.toString(StandardCharsets.UTF_8).split(System.lineSeparator());
//            if (outputLines.length > 1) {
//                return String.join(System.lineSeparator(), outputLines);
//            }
//            return outputLines[outputLines.length - 1];
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
