import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class Codechef {
    public static ArrayList<String[]> data;
    public static String[] headers;
    public static Scanner scanner = new Scanner(System.in);

    //------------------------------------------------------
    public static void filterData(ArrayList<String[]> data) {
        
        // Step1: Check if data is null or empty 
        if (data == null || data.isEmpty()) {
            System.out.println("No data available.");
            
            // Return if no data is available
            return;
        }

        System.out.print("Enter column name to filter: ");
        
        // Step2: Take the user input for column name
        String column = scanner.nextLine();
        
        // Column index of the column
        int columnIndex = -1;

        // Step3: Search through 'headers' to find matching column name
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(column)) {
                columnIndex = i;
                break;
            }
        }
        
        // If we not found a valid column
        if (columnIndex == -1) {
            System.out.println("Invalid column name!");
            
            // Return from the function
            return;
        }

        System.out.println("Enter value to filter by : " + headers[columnIndex]);
        
        // Step4: Take user input for filter value
        String value = scanner.nextLine();

        // Create new list to store filtered rows
        ArrayList<String[]> filteredData = new ArrayList<>();
        
        // Step5: Loop through each row in the original data to find value
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            if (row[columnIndex].equals(value)) {
                filteredData.add(row);
            }
        }

        // If we found no matching rows
        if (filteredData.isEmpty()) {
            System.out.println("No matching records found.");
        } else {
            System.out.println("\nFiltered Data:");
            
            // Step6: Display the filtered data using displayInfo() method
            displayInfo(filteredData);
        }
    }
    //------------------------------------------------------

    public static ArrayList<String[]> loadCSV(String filePath) {
        ArrayList<String[]> allData = new ArrayList<>();
        
        try {
            FileReader filereader = new FileReader(filePath);
            
            CSVReader reader = new CSVReader(filereader);
            
            headers = reader.readNext();
            
            String[] row = reader.readNext();
            while (row != null) {

                allData.add(row);
                row = reader.readNext();
            }
            System.out.println("CSV file loaded successfully!");
            return allData;
        } catch (Exception e) {
            System.out.println("Error loading CSV file.");
            return null;
        }
    }

    public static void displayInfo(ArrayList<String[]> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        System.out.println("\nInformation:");
        System.out.println("Total Rows: " + data.size());
        System.out.print("Columns: ");
        for (int i = 0; i < headers.length; i++) {
            System.out.print(headers[i] + " ");
        }
        System.out.println("\n");

        System.out.println("Data:");
        for (int i = 0; i < headers.length; i++) {
            System.out.print(headers[i] + "  ");
        }
        System.out.println();
        
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + "  ");
            }
            System.out.println();
        }
    }

    public static void basicStatistics(ArrayList<String[]> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        System.out.println("\nBasic Statistics:");

        for (int col = 0; col < headers.length; col++) {
            try {
                ArrayList<Double> values = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    String[] row = data.get(i);
                    if (row[col] != null && !row[col].isEmpty()) {
                        values.add(Double.parseDouble(row[col]));
                    }
                }
                
                if (!values.isEmpty()) {
                    System.out.println("\nColumn: " + headers[col]);
                    System.out.println("Total Non-Null Values: " + values.size());
                    
                    // Calculate sum
                    double sum = 0.0;
                    for (int i = 0; i < values.size(); i++) {
                        sum += values.get(i);
                    }
                    System.out.println("Mean (Average): " + String.format("%.2f", sum / values.size()));
                    
                    // Find minimum
                    double min = values.get(0);
                    for (int i = 0; i < values.size(); i++) {
                        if (values.get(i) < min) {
                            min = values.get(i);
                        }
                    }
                    System.out.println("Minimum Value: " + min);
                    
                    // Find maximum
                    double max = values.get(0);
                    for (int i = 0; i < values.size(); i++) {
                        if (values.get(i) > max) {
                            max = values.get(i);
                        }
                    }
                    System.out.println("Maximum Value: " + max);
                }
            } catch (Exception e) {
                continue; 
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to CSV Data Analyzer!\n");
        System.out.print("Enter CSV file name (data.csv): ");
        String filePath = scanner.nextLine();
        data = loadCSV(filePath);

        if (data == null) {
            return;
        }

        while (true) {
            System.out.println("\nCSV Data Analyzer:");
            System.out.println("1. Display Data Info");
            System.out.println("2. Show Basic Statistics");
            System.out.println("3. Filter Data");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                displayInfo(data);
            } else if (choice == 2) {
                basicStatistics(data);
            } else if (choice == 3) {
                filterData(data);
            }  else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again!");
            }
        }
    }
}
