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
