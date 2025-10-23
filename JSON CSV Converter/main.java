import org.json.*;
import com.opencsv.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Codechef {
    public static Scanner scanner = new Scanner(System.in);

    //--------------------------------------------------
    public static void jsonToCsv() {
        System.out.print("Enter input JSON file path: ");
        String jsonFile = scanner.nextLine().trim();
        System.out.print("Enter output CSV file path: ");
        String csvFile = scanner.nextLine().trim();
        
        JSONArray data = readJson(jsonFile);
        if (data != null && data.length() > 0) {
            if (writeCsv(csvFile, data)) {
                System.out.println("Successfully converted "+jsonFile+" to "+csvFile);
            }
        }
    }
    //--------------------------------------------------

    public static boolean writeCsv(String filePath, JSONArray data) {

        if (data == null || data.length() == 0) {
            System.out.println("No data to write");
            return false;
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            JSONObject first = data.getJSONObject(0);
            String[] headers = JSONObject.getNames(first);
            writer.writeNext(headers);

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                String[] row = new String[headers.length];
                for (int j = 0; j < headers.length; j++) {
                    row[j] = obj.getString(headers[j]);
                }
                writer.writeNext(row);
            }

            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to write CSV.");
            return false;
        }
    }

    public static boolean writeJson(String filePath, ArrayList<String[]> csvData) {
        if (csvData == null || csvData.size() < 2) {
            System.out.println("No data to write");
            return false;
        }

        try {
            String[] headers = csvData.get(0);
            JSONArray jsonArray = new JSONArray();

            for (int i = 1; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                JSONObject obj = new JSONObject();
                
                for (int j = 0; j < headers.length; j++) {
                    obj.put(headers[j], row[j]); 
                }
                jsonArray.put(obj);
            }

            FileWriter file = new FileWriter(filePath);
            file.write(jsonArray.toString(2));
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to write JSON.");
            return false;
        }
    }

     public static JSONArray readJson(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            return new JSONArray(content);
        } catch (Exception e) {
            System.out.println("Error reading JSON file.");
            return null;
        }
    }

    public static ArrayList<String[]> readCSV(String filePath) {
        CSVReader reader = null;
        ArrayList<String[]> allData = new ArrayList<>();
        
        try {
            reader = new CSVReader(new FileReader(filePath));
            
            String[] row;
            while ((row = reader.readNext()) != null) {
                allData.add(row);
            }
            
            if (allData.isEmpty()) {
                System.out.println("CSV file is empty.");
                return null;
            }
            
            System.out.println("CSV file loaded successfully!");
            return allData;
        } catch (Exception e) {
            System.out.println("Error loading CSV file.");
            return null;
        }
    }

    public static void csvToJson() {
        System.out.print("Enter input CSV file path: ");
        String csvFile = scanner.nextLine().trim();
        System.out.print("Enter output JSON file path: ");
        String jsonFile = scanner.nextLine().trim();
        
        ArrayList<String[]> csvData = readCSV(csvFile);
        if (csvData != null && !csvData.isEmpty()) {
            if (writeJson(jsonFile, csvData)) {
                System.out.println("Successfully converted "+csvFile+" to "+jsonFile);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to JSON CSV Converter!\n");
        while (true) {
            System.out.println("JSON CSV Converter");
            System.out.println("1. Convert CSV to JSON");
            System.out.println("2. Convert JSON to CSV");
            System.out.println("3. Exit");
            
            System.out.print("Enter your choice (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 1) {
                csvToJson();
            } else if (choice == 2) {
                jsonToCsv();
            } else if (choice == 3) {
                System.out.println("Exiting the program. Goodbye!");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
