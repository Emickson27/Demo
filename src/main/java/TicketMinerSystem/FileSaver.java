import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
 * FileSaver.java
 * Class to save updated event and customer data to CSV files.
 * Creates an output folder if it doesn't exist.
 * Saves event data including seat sales and revenue.
 * Saves customer data including money available and total saved.
 * Logs actions and errors during the save process.
 */

public class FileSaver {

    // Folder where the updated files will be saved
    private static final String OUTPUT_FOLDER = "updated_data";

    public static void saveUpdatedEvents(List<Event> events, String fileName) {
        ensureFolderExists();

        String filePath = OUTPUT_FOLDER + "/" + fileName;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("EventID,Name,Date,Time,VIP Seats Sold,Gold Seats Sold,Silver Seats Sold,Bronze Seats Sold,General Seats Sold,Total VIP Revenue,Total Gold Revenue,Total Silver Revenue,Total Bronze Revenue,Total General Revenue,Total Revenue,Total Tax Collected\n");

            for (Event e : events) {
                double vipRev = EventFinanceManager.getVipRevenue(e);
                double goldRev = EventFinanceManager.getGoldRevenue(e);
                double silverRev = EventFinanceManager.getSilverRevenue(e);
                double bronzeRev = EventFinanceManager.getBronzeRevenue(e);
                double generalRev = EventFinanceManager.getGeneralRevenue(e);
                double totalRev = EventFinanceManager.calculateTotalRevenue(e);

                writer.write(
                        e.getEventId() + "," +
                        e.getName() + "," +
                        e.getDate() + "," +
                        e.getTime() + "," +
                        e.getVipSeatsSold() + "," +
                        e.getGoldSeatsSold() + "," +
                        e.getSilverSeatsSold() + "," +
                        e.getBronzeSeatsSold() + "," +
                        e.getGeneralSeatsSold() + "," +
                        String.format("%.2f", vipRev) + "," +
                        String.format("%.2f", goldRev) + "," +
                        String.format("%.2f", silverRev) + "," +
                        String.format("%.2f", bronzeRev) + "," +
                        String.format("%.2f", generalRev) + "," +
                        String.format("%.2f", totalRev) + "," +
                        String.format("%.2f", e.getTotalTaxCollected()) + "\n"
                );
            }

            System.out.println("Updated event list saved to: " + filePath);
            Logger.log("Updated event list saved to: " + filePath);
        } catch (IOException ex) {
            System.out.println("Error saving updated event list: " + ex.getMessage());
            Logger.log("Error saving updated event list: " + ex.getMessage());
        }
    }

    public static void saveUpdatedCustomers(List<Customer> customers, String fileName) {
        ensureFolderExists();

        String filePath = OUTPUT_FOLDER + "/" + fileName;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("CustomerID,FirstName,LastName,Username,MoneyAvailable,ConcertsPurchased,TotalSaved\n");

            for (Customer c : customers) {
                writer.write(
                        c.getId() + "," +
                        c.getFirstName() + "," +
                        c.getLastName() + "," +
                        c.getUsername() + "," +
                        String.format("%.2f", c.getMoneyAvailable()) + "," +
                        c.getConcertsPurchased() + "," +
                        String.format("%.2f", c.getTotalSaved()) + "\n"
                );
            }

            System.out.println("Updated customer list saved to: " + filePath);
            Logger.log("Updated customer list saved to: " + filePath);
        } catch (IOException ex) {
            System.out.println("Error saving updated customer list: " + ex.getMessage());
            Logger.log("Error saving updated customer list: " + ex.getMessage());
        }
    }

    // Helper to make sure the folder exists
    private static void ensureFolderExists() {
        File folder = new File(OUTPUT_FOLDER);
        if (!folder.exists()) {
            folder.mkdir(); // creates the "updated_data" folder
        }
    }
}


