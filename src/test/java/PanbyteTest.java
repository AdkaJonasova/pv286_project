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
