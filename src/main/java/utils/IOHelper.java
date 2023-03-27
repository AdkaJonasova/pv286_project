package utils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOHelper {

    private static final String DEFAULT_DELIMITER = System.lineSeparator();

    private IOHelper() { }

    public static List<String> readFromStandardInput(String delimiter) {
        return readData(System.in, delimiter);
    }

    public static List<String> readFromFile(String filePath, String delimiter) throws IOException {
        try (InputStream stream = new FileInputStream(filePath)){
            return readData(stream, delimiter);
        } catch (IOException e) {
            throw new IOException(String.format("Unable to read input from: %s", filePath));
        }
    }

    private static List<String> readData(InputStream inputStream, String delimiter) {
        var result = new ArrayList<String>();

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter(resolveDelimiter(delimiter));
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        scanner.close();
        return result;
    }

    public static void writeToStandardOutput(List<String> text, String delimiter) {
        for (int i = 0; i < text.size(); i++) {
            System.out.print(text.get(i));
            if (i != text.size() - 1)
                System.out.print(resolveDelimiter(delimiter));
        }
    }

    public static void writeToFile(List<String> text, String filePath, String delimiter) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                if (i != text.size() - 1)
                    writer.write(resolveDelimiter(delimiter));
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
