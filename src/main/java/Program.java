import input.InputParser;

public class Program {

    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        var result = inputParser.parse(args);
        System.out.println(result.toString());
    }
}
