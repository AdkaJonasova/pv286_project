package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

    public List<String> readFromFile(String filePath, String delimiter) {
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

}
