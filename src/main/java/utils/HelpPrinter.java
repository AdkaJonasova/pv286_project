package utils;

public class HelpPrinter {

    public static void printHelp() {
        StringBuilder builder = new StringBuilder();

        builder.append("Allowed formats are: \n");
        for (Format format : Format.values()) {
            builder.append("- ")
                   .append(format.getDescription())
                   .append("\n");
        }
        builder.append("\n");

        builder.append("Allowed options for int format are: \n")
               .append("-> For input and output options \n");
        for (IntOption intOption : IntOption.values()) {
            builder.append("    - ")
                   .append(intOption.getDescription())
                   .append("\n");
        }
        builder.append("\n");

        builder.append("Allowed options for bits format are: \n")
                .append("-> For input options \n");
        for (BitsOption bitOption : BitsOption.values()) {
            builder.append("    - ")
                   .append(bitOption.getDescription())
                   .append("\n");
        }
        builder.append("\n");

        String allowedFlags = "Allowed flags are: \n " +
                "- -f FORMAT = Set input data format \n " +
                "- --from-options=OPTIONS = Set input options \n " +
                "- -t FORMAT = Set output data format \n " +
                "- --to-options=OPTIONS = Set output options \n " +
                "- -i FILE = Set input file (default is standard input) \n " +
                "- -o FILE = Set output file (default is standard output) \n " +
                "- -d DELIMITER = Record delimiter (default is newline) \n " +
                "- -h = Print help \n\n";

        builder.append(allowedFlags);
        System.out.println(builder);
    }
}
