package input;

import java.util.List;

public class InputParser {

    private boolean fromFlag = false;
    private boolean toFlag = false;
    private boolean inputFileFlag = false;
    private boolean outputFileFlag = false;
    private boolean delimiterFlag = false;
    private boolean helpFlag = false;

    private String fromRepresentation = "";
    private String toRepresentation = "";
    private String fromOptions = "";
    private String toOptions = "";
    private String delimiter = "";

    public void parse(String[] input) {
        for (var argument : input) {
            if (fromFlag || toFlag) {
                parseOptions(argument);
                resetFromToFlags();
            } else if (argument.startsWith("-")) {
                parseFlag(argument);
            } else {
                parseValue(argument);
                resetFlags();
            }
        }
        System.out.println(fromRepresentation + " " + toRepresentation);
        System.out.println(fromOptions + " " + toOptions);
    }

    private void parseFlag(String argument) {
        if (argument.equals("-f")) {
            fromFlag = true;
        } else if (argument.equals("-t")) {
            toFlag = true;
        } else if (argument.equals("-i")) {
            inputFileFlag = true;
        } else if (argument.equals("-o")) {
            outputFileFlag = true;
        } else if (argument.equals("-d")) {
            delimiterFlag = true;
        } else if (argument.equals("-h")) {
            helpFlag = true;
        } else {
            // chyba
        }
    }

    private void parseValue(String argument) {
        if (fromFlag && checkFormat(argument)) {
            fromRepresentation = argument;
        } else if (toFlag && checkFormat(argument)) {
            toRepresentation = argument;
        } else if (delimiterFlag) {
            delimiter = argument;
        } else {
            // chyba
        }
    }

    private void parseOptions(String argument) {
        if (fromFlag && argument.startsWith("--from-options")) {
            var argumentParts = argument.split("=");
            fromOptions = argumentParts[1];
        } else if (toFlag && argument.startsWith("--to-options")) {
            var argumentParts = argument.split("=");
            toOptions = argumentParts[1];
        }
    }

    private void resetFlags() {
        inputFileFlag = false;
        outputFileFlag = false;
        delimiterFlag = false;
        helpFlag = false;
    }

    private void resetFromToFlags() {
        fromFlag = false;
        toFlag = false;
    }

    private boolean checkFormat(String format) {
        List<String> possibleFormats = List.of("bytes", "hex", "int", "bits", "array");
        return possibleFormats.contains(format);
    }

    private boolean checkOption(String option) {
        List<String> possibleOptions = List.of("big", "little");
        return possibleOptions.contains(option);
    }
}
