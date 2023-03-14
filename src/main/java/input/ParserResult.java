package input;

public class ParserResult {

    private final String from;
    private final String to;
    private final String fromOption;
    private final String toOption;
    private final String inputFile;
    private final String outputFile;
    private final String delimiter;
    private final boolean shouldPrintHelp;

    public ParserResult(String from, String to, String fromOption, String toOption, String inputFile, String outputFile,
                        String delimiter, boolean shouldPrintHelp) {
        this.from = from;
        this.to = to;
        this.fromOption = fromOption;
        this.toOption = toOption;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.delimiter = delimiter;
        this.shouldPrintHelp = shouldPrintHelp;
    }

    //region Getters
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFromOption() {
        return fromOption;
    }

    public String getToOption() {
        return toOption;
    }

    public boolean getShouldPrintHelp() {
        return shouldPrintHelp;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getDelimiter() {
        return delimiter;
    }
    //endregion

    @Override
    public String toString() {
        var formatString = "From = %s, From options = %s \n " +
                "To = %s, To options = %s \n" +
                "Input file = %s, Output file = %s \n" +
                "Delimiter = %s, Print help = %s";
        return String
                .format(formatString, from, fromOption, to, toOption, getInputFile(), getOutputFile(), getDelimiter(), shouldPrintHelp);
    }
}
