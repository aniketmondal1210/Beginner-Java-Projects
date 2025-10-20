import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Codechef {

    static final String LOG_FILE = "logs.txt";
    static final String[] LOG_LEVELS = {"INFO", "WARNING", "ERROR"};
    static final String[] MESSAGES = {
        "Application started",
        "Low memory warning",
        "Database connection failed",
        "User logged in",
        "File not found",
        "Network timeout",
        "Disk space running low",
        "Unauthorized access attempt"
    };

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // 1. Generate a new log
    public static void generateLog() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String level = LOG_LEVELS[random.nextInt(LOG_LEVELS.length)];
        String message = MESSAGES[random.nextInt(MESSAGES.length)];
        String logEntry = timestamp + " [" + level + "] " + message;

        try {
            FileWriter writer = new FileWriter(LOG_FILE, true);
            writer.write(logEntry + "\n");
            writer.close();
            System.out.println("New log entry added!");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    // 2. View all logs
    public static void viewLogs() {
        try {
            ArrayList<String> logs = new ArrayList<>(Files.readAllLines(Paths.get(LOG_FILE)));
            if (logs.size() == 0) {
                System.out.println("No logs found.");
                return;
            }

            System.out.println("\n--- Log Entries ---");
            for (int i = 0; i < logs.size(); i++) {
                System.out.println(logs.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error reading the log file.");
        }
    }

    // 3. Filter logs by level
    public static void filterLogsByLevel() {
        System.out.print("Enter log level (INFO, WARNING, ERROR): ");
        String inputLevel = scanner.nextLine().trim().toUpperCase();

        boolean valid = false;
        for (int i = 0; i < LOG_LEVELS.length; i++) {
            if (LOG_LEVELS[i].equals(inputLevel)) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            System.out.println("Invalid log level!");
            return;
        }

        try {
            ArrayList<String> logs = new ArrayList<>(Files.readAllLines(Paths.get(LOG_FILE)));
            boolean found = false;

            System.out.println("\n--- " + inputLevel + " Logs ---");
            for (int i = 0; i < logs.size(); i++) {
                String log = logs.get(i).trim();
                if (log.contains("[" + inputLevel + "]")) {
                    System.out.println(log);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No " + inputLevel + " logs found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the log file.");
        }
    }

    // 4. Search logs by keyword
    public static void searchLogs() {
        System.out.print("Enter keyword to search in logs: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        try {
            ArrayList<String> logs = new ArrayList<>(Files.readAllLines(Paths.get(LOG_FILE)));
            boolean found = false;

            System.out.println("\n--- Logs containing '" + keyword + "' ---");
            for (int i = 0; i < logs.size(); i++) {
                String log = logs.get(i).trim();
                if (log.toLowerCase().contains(keyword)) {
                    System.out.println(log);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No logs found containing '" + keyword + "'.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the log file.");
        }
    }

    // 5. Log Statistics
    public static void logStatistics() {
        try {
            ArrayList<String> logs = new ArrayList<>(Files.readAllLines(Paths.get(LOG_FILE)));
            int total = logs.size();

            if (total == 0) {
                System.out.println("No logs found to analyze.");
                return;
            }

            int infoCount = 0, warningCount = 0, errorCount = 0;

            for (int i = 0; i < total; i++) {
                String log = logs.get(i);
                if (log.contains("[INFO]")) infoCount++;
                else if (log.contains("[WARNING]")) warningCount++;
                else if (log.contains("[ERROR]")) errorCount++;
            }

            String mostRecentLog = logs.get(total - 1).trim();

            System.out.println("\n--- Log Statistics ---");
            System.out.println("Total logs: " + total);
            System.out.println("INFO logs: " + infoCount);
            System.out.println("WARNING logs: " + warningCount);
            System.out.println("ERROR logs: " + errorCount);
            System.out.println("Most recent log: " + mostRecentLog);

        } catch (IOException e) {
            System.out.println("Error reading the log file.");
        }
    }


    // 6. User choice handler
    public static void userChoice(String choice) {
        switch (choice) {
            case "1":
                generateLog();
                break;
            case "2":
                viewLogs();
                break;
            case "3":
                filterLogsByLevel();
                break;
            case "4":
                searchLogs();
                break;
            case "5":
                logStatistics();
                break;
            case "6":
                System.out.println("Exiting... Goodbye!");
                break;
            default:
                System.out.println("Invalid choice! Try again.");
        }
    }

    // Main menu
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nLog File Parser");
            System.out.println("1. Generate a new log entry");
            System.out.println("2. View all logs");
            System.out.println("3. Filter logs by level");
            System.out.println("4. Search logs by keyword");
            System.out.println("5. View Log Statistics");
            System.out.println("6. Exit");

            System.out.print("Choose an option (1-6): ");
            String choice = scanner.nextLine().trim();
            userChoice(choice);

            if (choice.equals("6")) {
                break;
            }
        }
    }
}
