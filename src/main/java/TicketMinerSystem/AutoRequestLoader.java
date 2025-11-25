import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
 * AutoRequestLoader is responsible for loading auto-purchase requests from a CSV file.
 * Each request includes the username, event ID, ticket type, and quantity.
 * The loaded requests are returned as a list of AutoRequest objects.
 * Used for processing bulk ticket purchases.
 * Expected CSV format:
 * FirstName,LastName, ..., EventID, ..., Quantity, TicketType
 * (Columns are based on the provided sheet layout)
 * The username is constructed by combining first and last names.
 * Example row:
 * John,Doe,...,101,...,2,VIP
 * This would create an AutoRequest for user "johndoe" to purchase 2 VIP tickets for event ID 101.
 * The loader skips malformed or incomplete rows and logs the number of successfully loaded requests.
 */
public class AutoRequestLoader {

    public static List<AutoRequest> loadAutoRequests(String filename) {
        List<AutoRequest> requests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            int count = 0;

            while ((line = br.readLine()) != null) {

                // Skip header row
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                // Split CSV by commas
                String[] parts = line.split(",");

                try {
                    // Validate we have enough columns
                    if (parts.length < 7) {
                        System.out.println("Skipped incomplete row: " + line);
                        continue;
                    }

                    // Extract fields based on your sheet layout
                    String firstName = parts[0].trim();
                    String lastName = parts[1].trim();
                    int eventId = Integer.parseInt(parts[3].trim()); // Column D
                    int quantity = Integer.parseInt(parts[5].trim()); // Column F
                    String ticketType = parts[6].trim(); // Column G

                    // Combine names to form username (customizable)
                    String username = (firstName + lastName).replaceAll("\\s+", "").toLowerCase();

                    // Add to list
                    requests.add(new AutoRequest(username, eventId, ticketType, quantity));
                    count++;

                } catch (NumberFormatException ex) {
                    System.out.println("Skipped invalid numeric value in line: " + line);
                } catch (Exception ex) {
                    System.out.println("Skipped malformed row: " + line);
                }
            }

            System.out.println(count + " auto-purchase requests loaded from " + filename);
            Logger.log("Loaded " + count + " auto-purchase requests from " + filename);

        } catch (IOException e) {
            System.out.println("ERROR: Could not load file: " + filename);
            e.printStackTrace();
        }

        return requests;
    }
}

