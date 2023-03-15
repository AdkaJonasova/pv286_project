package utils;

public enum Flag {

    FROM("-f", "--from", "-> -f FORMAT or --from=FORMAT -> Set input data format"),
    TO("-t", "--to", "-> -t FORMAT or --to=FORMAT -> Set output data format"),
    FROM_OPTIONS("", "--from-options", "-> --from-options=OPTIONS -> Set input options"),
    TO_OPTIONS("", "--to-options", "-> --to-options=OPTIONS -> Set output options"),
    INPUT_FILE("-i", "--input", "-> -i FILE or --input=FILE -> Set input file (default is standard input)"),
    OUTPUT_FILE("-o", "--output", "-> -o FILE or --output=FILE -> Set output file (default is standard output)"),
    DELIMITER("-d", "--delimiter", "-> -d DELIMITER or --delimiter=DELIMITER -> Record delimiter (default is newline)"),
    HELP("-h", "--help", "-> -h or --help -> Print help");

    Flag(String shortVersion, String longVersion, String description) {
        this.shortVersion = shortVersion;
        this.longVersion = longVersion;
        this.description = description;
    }

    private final String shortVersion;
    private final String longVersion;
    private final String description;

    public String getShortVersion() {
        return shortVersion;
    }

    public String getLongVersion() {
        return longVersion;
    }

    public String getDescription() {
        return description;
    }

    public String getByShort(String shortFlag) {
        for (var flag : Flag.values()) {
            if (flag.shortVersion.equals(shortFlag)) {
                return flag.shortVersion;
            }
        }
        return null;
    }

    public String getByLong(String shortFlag) {
        for (var flag : Flag.values()) {
            if (flag.longVersion.equals(shortFlag)) {
                return flag.longVersion;
            }
        }
        return null;
    }
}
