import java.io.*;
import java.util.*;


/*
 * CustomerLoader.java
 * Class to load customer data from a CSV file.
 * Parses the CSV and creates Customer objects.
 * Handles errors gracefully and logs actions.
 * Expected CSV format:
 * ID,FirstName,LastName,MoneyAvailable,ConcertsPurchased,TicketMinerMembership,Username,Password
 */
public class CustomerLoader {

    public static List<Customer> loadCustomers(String fileName) {
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) throw new IOException("CSV file is empty");

            String[] headers = headerLine.trim().split("\\s*,\\s*");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    String columnName = headers[i].trim().toLowerCase().replace(" ", "");
                    String columnValue = values[i].trim();
                    row.put(columnName, columnValue);
                }

                int id = (int) Double.parseDouble(row.getOrDefault("id", "0").trim());
                String firstName = row.getOrDefault("firstname", "Unknown");
                String lastName = row.getOrDefault("lastname", "Unknown");
                double moneyAvailable = Double.parseDouble(row.getOrDefault("moneyavailable", "0"));
                int concertsPurchased = (int) Double.parseDouble(row.getOrDefault("concertspurchased", "0"));
                boolean ticketMinerMembership = Boolean.parseBoolean(row.getOrDefault("ticketminermembership", "false"));
                String username = row.getOrDefault("username", "guest");
                String password = row.getOrDefault("password", "guest123");

                customers.add(new Customer(id, firstName, lastName, moneyAvailable,
                        concertsPurchased, ticketMinerMembership, username, password));
            }

            System.out.println(customers.size() + " customers loaded from " + fileName);
            Logger.log("Loaded " + customers.size() + " customers from " + fileName);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            Logger.log("Error reading customers CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid numeric format in customer file: " + e.getMessage());
            Logger.log("Invalid numeric format in customers CSV: " + e.getMessage());
        }

        return customers;
    }
}

