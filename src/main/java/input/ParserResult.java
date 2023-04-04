package input;

import format.Format;
import options.IOption;

public class ParserResult {

    private final Format from;
    private final Format to;
    private final IOption[] fromOption;
    private final IOption[] toOption;
    private final String inputFile;
    private final String outputFile;
    private final String delimiter;
    private final boolean shouldPrintHelp;

    public ParserResult(Format from, Format to, IOption[] fromOption, IOption[] toOption, String inputFile, String outputFile,
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
    public Format getFrom() {
        return from;
    }

    public Format getTo() {
        return to;
    }

    public IOption[] getFromOptions() {
        return fromOption;
    }

    public IOption[] getToOptions() {
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
}
