import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanbyteTest {

    @Test
    public void testFromHexToBytes() {
        String echo = "74657374";
        String[] args = {"-f", "hex", "-t", "bytes"};
        String expectedOutput = "test";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromBytesToHex() {
        String echo = "test";
        String[] args = {"-f", "bytes", "-t", "hex"};
        String expectedOutput = "74657374";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromSpacedHexToBytes() {
        String echo = "74 65 73 74";
        String[] args = {"-f", "hex", "-t", "bytes"};
        String expectedOutput = "test";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromIntToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "-t", "hex"};
        String expectedOutput = "499602d2";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromIntOptionsBigToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "--from-options=big" ,"-t", "hex"};
        String expectedOutput = "499602d2";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromIntOptionsLittleToHex() {
        String echo = "1234567890";
        String[] args = {"-f", "int", "--from-options=little" ,"-t", "hex"};
        String expectedOutput = "d2029649";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromHexToInt() {
        String echo = "499602d2";
        String[] args = {"-f", "hex","-t", "int"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromHexToIntOptionsBig() {
        String echo = "499602d2";
        String[] args = {"-f", "hex","-t", "int" ,"--to-options=big"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromHexToIntOptionsLittle() {
        String echo = "d2029649";
        String[] args = {"-f", "hex","-t", "int" ,"--to-options=little"};
        String expectedOutput = "1234567890";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromBitsToBytes() {
        String echo = "100 1111 0100 1011";
        String[] args = {"-f", "bits","-t", "bytes"};
        String expectedOutput = "OK";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromBitsOptionsLeftToBytes() {
        String echo = "100111101001011";
        String[] args = {"-f", "bits", "--from-options=left", "-t", "bytes"};
        String expectedOutput = "OK";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromBitsOptionsRightToHex() {
        String echo = "100111101001011";
        String[] args = {"-f", "bits", "--from-options=right", "-t", "hex"};
        String expectedOutput = "9e96";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromBytesToBits() {
        String echo = "OK";
        String[] args = {"-f", "bytes", "-t", "bits"};
        String expectedOutput = "0100111101001011";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromHexToArray() {
        String echo = "01020304";
        String[] args = {"-f", "hex", "-t", "array"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToHex() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "hex"};
        String expectedOutput = "01020304";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArray() {
        String echo = "{0x01,2,0b11 ,'\\x04' }";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsHexRepresentation() {
        String echo = "[0x01, 2, 0b11, '\\x04']";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0x"};
        String expectedOutput = "{0x1, 0x2, 0x3, 0x4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsDecimalRepresentation() {
        String echo = "(0x01, 2, 0b11, '\\x04')";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0"};
        String expectedOutput = "{1, 2, 3, 4}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsCharRepresentation() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "array", "--to-options=a"};
        String expectedOutput = "{'\\x01', '\\x02', '\\x03', '\\x04'}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsBinaryRepresentation() {
        String echo = "[0x01, 2, 0b11, '\\x04']";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0b"};
        String expectedOutput = "{0b1, 0b10, 0b11, 0b100}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsRegularBrackets() {
        String echo = "(0x01, 2, 0b11, '\\x04')";
        String[] args = {"-f", "array", "-t", "array", "--to-options=\"(\""};
        String expectedOutput = "(0x1, 0x2, 0x3, 0x4)";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromArrayToArrayOptionsDecRepSquareBracket() {
        String echo = "{0x01, 2, 0b11, '\\x04'}";
        String[] args = {"-f", "array", "-t", "array", "--to-options=0", "\\", "--to-options=\"[\""};
        String expectedOutput = "[1, 2, 3, 4]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNestedFromArrayToArray() {
        String echo = "[[1, 2], [3, 4], [5, 6]]";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNestedFromArrayToArrayOptionsCurlyBracketsDecimalRepresentation() {
        String echo = "[[1, 2], [3, 4], [5, 6]]";
        String[] args = {"-f", "array", "-t", "array", "\\", "--to-options=\"{\"", "--to-options=0"};
        String expectedOutput = "{{1, 2}, {3, 4}, {5, 6}}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNestedFromArrayToArrayOptionsDecimalRepresentationSquareBrackets() {
        String echo = "{{0x01, (2), [3, 0b100, 0x05], '\\x06'}}";
        String[] args = {"-f", "array", "-t", "array", "\\", "--to-options=0", "--to-options==\"[\""};
        String expectedOutput = "[[1, [2], [3, 4, 5], 6]]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNestedFromArrayToArrayEmptyBrackets() {
        String echo = "()";
        String[] args = {"-f", "array", "-t", "array"};
        String expectedOutput = "{}";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testNestedFromArrayToArrayOptionsSquareBrackets() {
        String echo =  "([],{})";
        String[] args = {"-f", "array", "-t", "array", "--to-options=\"[\""};
        String expectedOutput = "[[], []]";
        String actualOutput = getOutputOfProgramCall(echo, args);
        assertEquals(expectedOutput, actualOutput);
    }

    private String getOutputOfProgramCall(String echo, String[] args) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(echo.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Program.main(args);
        String[] outputLines = byteArrayOutputStream.toString().split(System.lineSeparator());
        return outputLines[outputLines.length - 1];
    }
}
