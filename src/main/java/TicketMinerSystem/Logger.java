import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/*
 * Logger.java
 * Class to log actions and errors in the Ticket Miner System.
 * Creates a log_actions folder if it doesn't exist.
 * Appends log entries with timestamps to a log.txt file.
 * Handles IO exceptions gracefully.
 */

public class Logger {
    // Define the folder and log file path
    /*
     * @param LOG_FOLDER Folder to store log files
     * @param LOG_FILE Path to the log file within the log folder
     * Used for logging actions and errors in the Ticket Miner System
     */

    private static final String LOG_FOLDER = "log_actions";
    private static final String LOG_FILE = LOG_FOLDER + "/log.txt";

    public static void log(String action) {
        try {
            //Ensure the folder exists (create it if it doesn’t)
            File folder = new File(LOG_FOLDER);
            if (!folder.exists()) {
                folder.mkdir(); // creates the "log_actions" folder
            }

            //Create the file writer in append mode
            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                String timestamp = LocalDateTime.now().format(formatter);
                writer.write("[" + timestamp + "] " + action + "\n");
            }

        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

