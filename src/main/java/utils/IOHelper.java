package utils;

import java.io.File;
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

    public static List<String> readFromFile(String filePath, String delimiter) {
        try (InputStream stream = new FileInputStream(filePath)){
            return readData(stream, delimiter);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static List<String> readData(InputStream inputStream, String delimiter) {
        var result = new ArrayList<String>();

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter(resolverDelimiter(delimiter));
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
                System.out.print(resolverDelimiter(delimiter));
        }
    }

    public static void writeToFile(List<String> text, String filePath, String delimiter) {
        try (FileWriter writer = new FileWriter(new File(filePath), false)) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                if (i != text.size() - 1)
                    writer.write(resolverDelimiter(delimiter));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String resolverDelimiter(String delimiter) {
        return delimiter.isEmpty() ? DEFAULT_DELIMITER : delimiter;
    }
}
