import exception.InputParsingException;
import input.InputParser;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        try {
            var result = inputParser.parse(args);
            System.out.println(result.toString());
        } catch (InputParsingException e) {
            System.out.println("Chyba");
        }
    }
}
