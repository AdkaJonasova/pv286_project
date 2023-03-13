package input;

public class ParserResult {

    private String from;
    private String to;
    private String fromOption;
    private String toOption;
    private boolean shouldPrintHelp;

    public ParserResult(String from, String to, String fromOption, String toOption, boolean shouldPrintHelp) {
        this.from = from;
        this.to = to;
        this.fromOption = fromOption;
        this.toOption = toOption;
        this.setShouldPrintHelp(shouldPrintHelp);
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
    //endregion

    @Override
    public String toString() {
        var formatString = "From = %s, To = %s, From options = %s, To options = %s, Print help = %s";
        return String.format(formatString, from, to, fromOption, toOption, shouldPrintHelp);
    }


}
