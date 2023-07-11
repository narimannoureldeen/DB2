

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    public CSVWriter(String[][] csv, String s) {
        writeCSV(csv,s);
    }

    public static void writeCSV(String[][] data, String filename) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(filename);
            for (String[] row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating CSV file.");
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error closing CSV file.");
                e.printStackTrace();
            }
        }
    }
}