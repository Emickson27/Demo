/*
 * RunTicket.java
 * Main class to run the Ticket Miner System.
 * Loads event and customer data, processes automatic purchases, and provides a menu for manual operations.
 * Saves updated data back to CSV files upon completion.
 * Coordinates various components of the Ticket Miner System.
 * Initializes the system and manages the overall workflow.
 * Handles both automatic and manual ticket purchase processes.
 * Ensures data integrity and persistence through file operations.
 * 
 * @version 4.0
 * @author Eloy Perez Quinonez
 * @author Diego Sanchez
 * @author Jesus Munoz
 * @author Kevin Vilchis
 * @date November 18, 2025
 */

import java.util.ArrayList;
import java.util.List;

public class RunTicket {

    public static void main(String[] args) {

        // ==========================================
        // LOAD ORIGINAL EVENT + CUSTOMER DATA
        // ==========================================
        List<Event> events = EventLoader.loadEvents("data/EventListPA3(Sheet1).csv");
        List<Customer> customers = CustomerLoader.loadCustomers("data/CustomerListPA3(Sheet1).csv");
        List<Invoice> invoices = new ArrayList<>();

        if (events.isEmpty() || customers.isEmpty()) {
            System.out.println("Error: Could not load base data. Exiting.");
            return;
        }

        Logger.log("System started. Loaded " + events.size() + " events and " + customers.size() + " customers.");

        // ==========================================
        // INITIALIZE AUTO-PURCHASE ENGINE
        // ==========================================
        AutoPurchaseProcessor autoProcessor = new AutoPurchaseProcessor(events, customers, invoices);

        // ==========================================
        //LOAD AUTO PURCHASE FILE (CSV)
        // ==========================================
        List<AutoRequest> autoRequests = AutoRequestLoader.loadAutoRequests("data/AutoPurchas10k.csv");

        if (!autoRequests.isEmpty()) {
            Logger.log("Processing " + autoRequests.size() + " auto-purchase requests...");
            autoProcessor.processAllAutoRequests(autoRequests);
            FileSaver.saveUpdatedEvents(events, "UpdatedEventList.csv");
            FileSaver.saveUpdatedCustomers(customers, "UpdatedCustomerList.csv");
            System.out.println("Finished running automatic purchases.\n");
        } else {
            System.out.println("No automatic purchase file found OR file is empty.");
        }

        

        // ==========================================
        // NORMAL MENU MODE
        // ==========================================
        MenuDisplay menu = new MenuDisplay(events, customers, invoices);
        menu.showMainMenu();

        // ==========================================
        // SAVE UPDATED DATA
        // ==========================================
        FileSaver.saveUpdatedEvents(events, "UpdatedEventList.csv");
        FileSaver.saveUpdatedCustomers(customers, "UpdatedCustomerList.csv");

        Logger.log("System terminated successfully.");
        System.out.println("System terminated successfully.");
    }
}
