package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    public static void write(String text) {
        // Specify the path of the file where you want to write the text
        String filePath = "output.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the text to the file
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
