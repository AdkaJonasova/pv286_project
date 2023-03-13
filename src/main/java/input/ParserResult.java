package input;

public class ParserResult {

    private String from;
    private String to;
    private String fromOption;
    private String toOption;

    public ParserResult(String from, String to, String fromOption, String toOption) {
        this.from = from;
        this.to = to;
        this.fromOption = fromOption;
        this.toOption = toOption;
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

    //endregion

    @Override
    public String toString() {
        return String.format("From = %s, To = %s, From options = %s, To options = %s", from, to, fromOption, toOption);
    }
}
