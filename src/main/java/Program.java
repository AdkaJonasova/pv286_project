import exception.InputParsingException;
import input.InputParser;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            printHelp();
            var result = inputParser.parse(args);
            System.out.println(result.toString());
        } catch (InputParsingException e) {
            System.out.println("Chyba");
        }
    }

    private static void printHelp() {
        String allowedFormats = "Allowed formats are: \n " +
                "- bytes = Raw bytes \n " +
                "- hex = Hex-encoded string \n " +
                "- int = Integer \n " +
                "- bits = 0,1-represented bits \n " +
                "- array = Byte array \n\n";

        String allowedOptionsInt = "Allowed options for int format are: \n " +
                "1) For input options: \n" +
                "   - big = Store the integer in big-endian representation (most significant byte at the lowest address) -> default \n" +
                "   - little = Store the integer in little-endian representation (least significant byte at the lowest address) \n" +
                "2) For output options: \n " +
                "   - big = Interpret bytes as an an integer in big-endian representation (most significant byte at the lowest address) -> default \n " +
                "   - little = Interpret bytes as an integer in little-endian representation (least significant byte at the lowest address) \n\n";

        String allowedOptionsBits = "Allowed options for bits format are: \n " +
                "1) For input options: \n " +
                "   - left = If necessary, pad input with zero bits from left -> default \n" +
                "   - right = If necessary, pad input with zero bits from right \n\n";

        String allowedOptionsArray = "Allowed options for array format are: \n" +
                "1) For output options: \n " +
                "   - 0x = Represent bytes as a 0x-prefixed hex number (e.g., 0xff) -> default \n " +
                "   - 0 = Represent bytes as a decimal number (e.g., 255) \n " +
                "   - a = Represent bytes as characters (e.g., 'a', '\\x00') \n " +
                "   - 0b = Represent bytes as 0b-prefixed binary number (e.g., 0b11111111) \n " +
                "   - { or } or {} = Use curly brackets in output -> default \n " +
                "   - [ or ] or [] = Use square brackets in output \n " +
                "   - ( or ) or () = Use regular brackets in output \n\n";

        String allowedFlags = "Allowed flags are: \n " +
                "- -f FORMAT = Set input data format \n " +
                "- --from-options=OPTIONS = Set input options \n " +
                "- -t FORMAT = Set output data format \n " +
                "- --to-options=OPTIONS = Set output options \n " +
                "- -i FILE = Set input file (default is standard input) \n " +
                "- -o FILE = Set output file (default is standard output) \n " +
                "- -d DELIMITER = Record delimiter (default is newline) \n " +
                "- -h = Print help \n\n";

        System.out.println(allowedFlags + allowedFormats + allowedOptionsInt + allowedOptionsBits + allowedOptionsArray);
    }
}
