package input;

import format.Format;
import options.IOption;

/**
 * This class provides storage for result from {@link InputParser#parse(String[])}} method.
 * It contains following fields:
 * <ul>
 *     <li>from - specifies from {@link Format}</li>
 *     <li>to - specifies to {@link Format}</li>
 *     <li>fromOptions - specifies options to use when converting from {@link #from} Format </li>
 *     <li>toOptions - specifies options to use when converting to {@link #to} Format </li>
 *     <li>input file - specifies path of file from which input should be read</li>
 *     <li>output file - specifies path of file to which output should be written</li>
 *     <li>delimiter - specifies delimiter which separates inputs and outputs</li>
 * </ul>
 */
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
