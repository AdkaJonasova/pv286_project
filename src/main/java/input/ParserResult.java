package input;

public class ParserResult {

    private String from;
    private String to;
    private String fromOption;
    private String toOption;
    private String inputFile;
    private String outputFile;
    private String delimiter;
    private boolean shouldPrintHelp;

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

    //region Setters and Getters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromOption() {
        return fromOption;
    }

    public void setFromOption(String fromOption) {
        this.fromOption = fromOption;
    }

    public String getToOption() {
        return toOption;
    }

    public void setToOption(String toOption) {
        this.toOption = toOption;
    }

    public boolean getShouldPrintHelp() {
        return shouldPrintHelp;
    }

    public void setShouldPrintHelp(boolean shouldPrintHelp) {
        this.shouldPrintHelp = shouldPrintHelp;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
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
