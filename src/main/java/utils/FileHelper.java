package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

    public static List<String> readFromFile(String filePath, String delimiter) {
        var result = new ArrayList<String>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(delimiter);
            while (scanner.hasNext()) {
                result.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeToFile(List<String> text, String filePath, String delimiter) {
        try (FileWriter writer = new FileWriter(new File(filePath), false)) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                if (i != text.size() - 1)
                    writer.write(delimiter);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
