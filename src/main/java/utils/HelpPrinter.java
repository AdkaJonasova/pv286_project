package utils;

import format.Format;
import options.BitsOption;
import options.IntOption;

public class HelpPrinter {

    public static void printHelp() {
        StringBuilder builder = new StringBuilder();

        printAllowedFormats(builder);
        printAllowedOptions(builder);
        printAllowedFlags(builder);

        System.out.println(builder);
    }

    private static void printAllowedFlags(StringBuilder builder) {
        builder.append("Allowed flags are: \n");
        for (Flag flag : Flag.values()) {
            builder.append("- ")
                    .append(flag.getDescription())
                    .append("\n");
        }
        builder.append("\n");
    }

    private static void printAllowedOptions(StringBuilder builder) {
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
    }

    private static void printAllowedFormats(StringBuilder builder) {
        builder.append("Allowed formats are: \n");
        for (Format format : Format.values()) {
            builder.append("- ")
                    .append(format.getDescription())
                    .append("\n");
        }
        builder.append("\n");
    }
}

