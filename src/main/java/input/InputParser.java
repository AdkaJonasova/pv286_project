package input;

public class InputParser {

    private boolean fromFlag = false;
    private boolean toFlag = false;
    private boolean fromOptionsFlag = false;
    private boolean toOptionsFlag = false;
    private boolean inputFileFlag = false;
    private boolean outputFileFlag = false;
    private boolean delimiterFlag = false;
    private boolean helpFlag = false;

    private String fromRepresentation = "";
    private String toRepresentation = "";
    private String fromOptions = "";
    private String toOptions = "";
    private String delimiter = "";

    private void parseFlag(String argument) {
        if (argument.equals("-f")) {
            fromFlag = true;
        } else if (argument.equals("-t")) {
            toFlag = true;
        } else if (argument.equals("-i")) {
            inputFileFlag = true;
        } else if (argument.equals("-o")) {
            outputFileFlag = true;
        } else if (argument.equals("-h")) {
            helpFlag = true;
        } else if (argument.startsWith("--from-options")) {
            fromOptionsFlag = true;
        } else if (argument.startsWith("--to-options")) {
            toOptionsFlag = true;
        } else {
            // chyba
        }
    }
}
