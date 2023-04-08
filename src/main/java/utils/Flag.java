package utils;

/**
 * This enum contains values of different flags. Each value consists of:
 * <ul>
 *     <li>short version (starting with -), if the value does not have short version it contains empty string</li>
 *     <li>long version (starting with --), if the value does not have long version it contains empty string</li>
 *     <li>description of the value</li>
 * </ul>
 */
public enum Flag {

    FROM("-f", "--from", "-> -f FORMAT or --from=FORMAT -> Set input data format"),
    TO("-t", "--to", "-> -t FORMAT or --to=FORMAT -> Set output data format"),
    FROM_OPTIONS("", "--from-options", "-> --from-options=OPTIONS -> Set input options"),
    TO_OPTIONS("", "--to-options", "-> --to-options=OPTIONS -> Set output options"),
    INPUT_FILE("-i", "--input", "-> -i FILE or --input=FILE -> Set input file (default is standard input)"),
    OUTPUT_FILE("-o", "--output", "-> -o FILE or --output=FILE -> Set output file (default is standard output)"),
    DELIMITER("-d", "--delimiter", "-> -d DELIMITER or --delimiter=DELIMITER -> Record delimiter (default is newline)"),
    HELP("-h", "--help", "-> -h or --help -> Print help");

    private final String shortVersion;
    private final String longVersion;
    private final String description;

    Flag(String shortVersion, String longVersion, String description) {
        this.shortVersion = shortVersion;
        this.longVersion = longVersion;
        this.description = description;
    }

    // region Getters
    public String getLongVersion() {
        return longVersion;
    }

    public String getDescription() {
        return description;
    }
    // endregion

    /**
     * Finds value by short version string representation.
     *
     * @param shortFlag Short version string representation.
     * @return Value corresponding to shortFlag or <code>null</code> if no such a value found.
     */
    public static Flag getByShort(String shortFlag) {
        for (var flag : Flag.values()) {
            if (flag.shortVersion.equals(shortFlag)) {
                return flag;
            }
        }
        return null;
    }

    /**
     * Finds value by long version string representation.
     *
     * @param longFlag Long version string representation.
     * @return Value corresponding to longFlag or <code>null</code> if no such a value found.
     */
    public static Flag getByLong(String longFlag) {
        for (var flag : Flag.values()) {
            if (flag.longVersion.equals(longFlag)) {
                return flag;
            }
        }
        return null;
    }
}
