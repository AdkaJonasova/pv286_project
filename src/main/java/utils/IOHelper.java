package utils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for handling IO operations such as reading and writing to file,
 * reading and writing to standard output.
 */
public final class IOHelper {

    private static final String DEFAULT_DELIMITER = System.lineSeparator();

    private IOHelper() { }

    /**
     * Reads from standard input using the delimiter to divide values.
     *
     * @param delimiter Delimiter which divides values.
     * @return List of values read from standard input.
     */
    public static List<String> readFromStandardInput(String delimiter) {
        return readData(System.in, delimiter);
    }

    /**
     * Reads from file at given filePath using the given delimiter to divide values.
     *
     * @param filePath Path of the file to read from.
     * @param delimiter Delimiter which divides values.
     * @return List of values read from given file.
     * @throws IOException If the given file does not exist or if any problems during reading from this file occur.
     */
    public static List<String> readFromFile(String filePath, String delimiter) throws IOException {
        try (InputStream stream = new FileInputStream(filePath)){
            return readData(stream, delimiter);
        } catch (IOException e) {
            throw new IOException(String.format("Unable to read input from: %s", filePath));
        }
    }

    private static List<String> readData(InputStream inputStream, String delimiter) {
        var result = new ArrayList<String>();

        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        scanner.useDelimiter(resolveDelimiter(delimiter));
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        scanner.close();
        return result;
    }

    /**
     * Writes given text to standard output using given delimiter to divide values.
     *
     * @param text List of values that should be written to standard output.
     * @param delimiter Delimiter which divides values.
     */
    public static void writeToStandardOutput(List<String> text, String delimiter) {
        for (int i = 0; i < text.size(); i++) {
            System.out.print(text.get(i));
            if (i != text.size() - 1){
                System.out.print(resolveDelimiter(delimiter));
            }
        }
        System.out.println();
    }

    /**
     * Writes given text into the file at the given filePath using the given delimiter.
     *
     * @param text List of values that should be written to file at the given path.
     * @param filePath Path of the file to write to.
     * @param delimiter Delimiter which divides values.
     * @throws IOException If the given file does not exist or if any problems during writing to this file occur.
     */
    public static void writeToFile(List<String> text, String filePath, String delimiter) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8, false)) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                if (i != text.size() - 1){
                    writer.write(resolveDelimiter(delimiter));
                }
            }
            writer.flush();
        } catch (IOException e) {
            throw new IOException(String.format("Unable to write output into: %s", filePath));
        }
    }

    private static String resolveDelimiter(String delimiter) {
        return delimiter.isEmpty() ? DEFAULT_DELIMITER : delimiter;
    }
}
