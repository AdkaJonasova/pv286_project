package input;

import format.Format;
import options.IOption;

import java.util.List;

public class ParserResult {

    private final Format from;
    private final Format to;
    private final List<IOption> fromOption;
    private final List<IOption> toOption;
    private final String inputFile;
    private final String outputFile;
    private final String delimiter;
    private final boolean shouldPrintHelp;

    public ParserResult(Format from, Format to, List<IOption> fromOption, List<IOption> toOption, String inputFile, String outputFile,
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

    public List<IOption> getFromOptions() {
        return fromOption;
    }

    public List<IOption> getToOptions() {
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
                .format(formatString, from, fromOption, to, toOption, inputFile, outputFile, delimiter, shouldPrintHelp);
    }
}
