import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanbyteTest {

    @Test
    public void testCorrectOutputWhenPassedCorrectSimpleInputAndArgsFromTo() {
        String echo = "74657374";
        String[] args = {"-f", "hex", "-t", "bytes"};
        String expectedOutput = "test";
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
