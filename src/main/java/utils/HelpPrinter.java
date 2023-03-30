package utils;

import format.Format;
import options.ArrayOption;
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
        builder.append("Allowed flags are: ")
                .append(System.lineSeparator());
        for (Flag flag : Flag.values()) {
            builder.append("- ")
                    .append(flag.getDescription())
                    .append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private static void printAllowedOptions(StringBuilder builder) {
        printIntOptions(builder);
        printBitsOptions(builder);
        printArraysOptions(builder);
    }

    private static void printArraysOptions(StringBuilder builder) {
        builder.append("Allowed options for arrays format are: ")
                .append(System.lineSeparator())
                .append("-> For output options ")
                .append(System.lineSeparator());
        for (ArrayOption arrayOption : ArrayOption.values()) {
            builder.append("    - ")
                    .append(arrayOption.getDescription())
                    .append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private static void printBitsOptions(StringBuilder builder) {
        builder.append("Allowed options for bits format are: ")
                .append(System.lineSeparator())
                .append("-> For input options ")
                .append(System.lineSeparator());
        for (BitsOption bitOption : BitsOption.values()) {
            builder.append("    - ")
                    .append(bitOption.getDescription())
                    .append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private static void printIntOptions(StringBuilder builder) {
        builder.append("Allowed options for int format are: ")
                .append(System.lineSeparator())
                .append("-> For input and output options ")
                .append(System.lineSeparator());
        for (IntOption intOption : IntOption.values()) {
            builder.append("    - ")
                    .append(intOption.getDescription())
                    .append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private static void printAllowedFormats(StringBuilder builder) {
        builder.append("Allowed formats are: ")
                .append(System.lineSeparator());
        for (Format format : Format.values()) {
            builder.append("- ")
                    .append(format.getDescription())
                    .append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }
}
