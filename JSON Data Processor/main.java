import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Codechef {

    static final String FILE_NAME = "students.json";
    static Scanner scanner = new Scanner(System.in);

    // Load students from the JSON file
    public static JSONArray loadData() {
        try {
            String content = Files.readString(Paths.get(FILE_NAME));
            return new JSONArray(content);
        } catch (Exception e) {
            System.out.println("Error: Unable to load data from " + FILE_NAME);
            return new JSONArray(); // Return empty if file not found or invalid
        }
    }

    // View all student records
    public static void viewStudents() {
        JSONArray students = loadData();
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");
        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            System.out.println("{" +
                "'id': " + student.getInt("id") + ", " +
                "'name': '" + student.getString("name") + "', " +
                "'age': " + student.getInt("age") + ", " +
                "'department': '" + student.getString("department") + "', " +
                "'year': '" + student.getString("year") + "', " +
                "'cgpa': " + student.getDouble("cgpa") + ", " +
                "'email': '" + student.getString("email") + "'" +
                "}");
        }
    }

    // Search student by ID
    public static void searchStudent() {
        JSONArray students = loadData();
        System.out.println("Enter student ID to search: ");
        String input = scanner.nextLine().trim();

        int studentId;
        try {
            studentId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numeric ID.");
            return;
        }

        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            if (student.getInt("id") == studentId) {
                System.out.println("\n--- Student Found ---");
                System.out.println("{" +
                "'id': " + student.getInt("id") + ", " +
                "'name': '" + student.getString("name") + "', " +
                "'age': " + student.getInt("age") + ", " +
                "'department': '" + student.getString("department") + "', " +
                "'year': '" + student.getString("year") + "', " +
                "'cgpa': " + student.getDouble("cgpa") + ", " +
                "'email': '" + student.getString("email") + "'" +
                "}");
                return;
            }
        }

        System.out.println("No student found with ID " + studentId + ".");
    }

    // Filter students based on CGPA or Age
    public static void filterStudents() {
        JSONArray students = loadData();
        System.out.print("Enter key to filter by (cgpa/age): ");
        String key = scanner.nextLine().trim();

        if (!key.equals("cgpa") && !key.equals("age")) {
            System.out.println("Invalid key! You can only filter by 'cgpa' or 'age'.");
            return;
        }

        System.out.print("Enter condition operator (>, <, >=, <=, ==): ");
        String operator = scanner.nextLine().trim();

        System.out.print("Enter value: ");
        String valueInput = scanner.nextLine().trim();

        double value;
        try {
            value = Double.parseDouble(valueInput);
        } catch (Exception e) {
            System.out.println("Invalid value. Please enter a numeric value.");
            return;
        }

        JSONArray filtered = new JSONArray();

        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            double studentValue = student.getDouble(key);

            boolean match = false;
            switch (operator) {
                case ">":
                    match = studentValue > value;
                    break;
                case "<":
                    match = studentValue < value;
                    break;
                case ">=":
                    match = studentValue >= value;
                    break;
                case "<=":
                    match = studentValue <= value;
                    break;
                case "==":
                    match = studentValue == value;
                    break;
                default:
                    match = false;
            }

            if (match) {
                filtered.put(student);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No students found with " + key + " " + operator + " " + value + ".");
        } else {
            System.out.println("\n--- Filtered Students ---");
            for (int i = 0; i < filtered.length(); i++) {
                JSONObject student = filtered.getJSONObject(i);
                System.out.println("{" +
                "'id': " + student.getInt("id") + ", " +
                "'name': '" + student.getString("name") + "', " +
                "'age': " + student.getInt("age") + ", " +
                "'department': '" + student.getString("department") + "', " +
                "'year': '" + student.getString("year") + "', " +
                "'cgpa': " + student.getDouble("cgpa") + ", " +
                "'email': '" + student.getString("email") + "'" +
                "}");
            }
        }
    }
    public static void deleteStudent() {
        JSONArray students = loadData();
        System.out.print("Enter student ID to delete: ");
        String input = scanner.nextLine().trim();

        int studentId;
        try {
            studentId = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        JSONArray updatedList = new JSONArray();
        boolean found = false;

        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            if (student.getInt("id") != studentId) {
                updatedList.put(student);
            } else {
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with ID " + studentId + ".");
            return;
        }

        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(updatedList.toString(4));
            writer.close();
            System.out.println("Student with ID " + studentId + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error writing to file.");
        }
    }

    // User choice handling
    public static void userChoice(String choice) {
        switch (choice) {
            case "1":
                viewStudents();
                break;
            case "2":
                searchStudent();
                break;
            case "3":
                filterStudents();
                break;
            case "4":
                deleteStudent();
                break;
            case "5":
                System.out.println("Exiting... Goodbye!");
                break;
            default:
                System.out.println("Invalid choice! Try again.");
        }
    }

    // Main menu
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nJSON Data Processor - Student Records");
            System.out.println("1. View all students");
            System.out.println("2. Search student by ID");
            System.out.println("3. Filter students by condition");
            System.out.println("4. Delete a student");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();
            userChoice(choice);
            if (choice.equals("5")) break;
        }
    }
}
