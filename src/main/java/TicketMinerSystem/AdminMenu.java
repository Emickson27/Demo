
import java.util.*;

/*
 * AdminMenu.java
 * UPDATED TO INCLUDE:
 *  - View Company Fee Revenue Per Event
 *  - View Company Fee Revenue For All Events
 *  - Cancel Event (refund all customers)
 */

public class AdminMenu {

    public static void showMenu(List<Event> events, List<Customer> customers, List<Invoice> invoices) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\n======================");
            System.out.println("===== ADMIN MENU =====");
            System.out.println("======================");
            System.out.println("A. Inquire event by ID");
            System.out.println("B. Inquire event by Name");
            System.out.println("C. Show All Events");
            System.out.println("D. View All Customers");
            System.out.println("E. View Total Tax Collected Per Event");
            System.out.println("F. Create New Event");
            System.out.println("--------------------------------------");
            System.out.println("H. View Fees Earned Per Event");
            System.out.println("I. View Total Fees Earned (All Events)");
            System.out.println("J. Cancel an Event");
            System.out.println("--------------------------------------");
            System.out.println("G. Return to Main Menu");
            System.out.print("Enter choice: ");
            choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A" -> inquireById(events);
                case "B" -> inquireByName(events);
                case "C" -> showAllEvents(events);
                case "D" -> showAllCustomers(customers);
                case "E" -> showEventTax(events);
                case "F" -> {
                    EventCreation creator = new EventCreation(events);
                    creator.createEventFromConsole();
                }

                // ===== NEW OPTIONS =====
                case "H" -> showEventFees(events);
                case "I" -> showTotalCompanyFees(events);
                case "J" -> cancelEvent(events, customers, invoices);

                case "G" -> {
                    System.out.println("Returning to Main Menu...");
                    Logger.log("Admin logged out.");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (!choice.equals("G"));
    }

    // =====================================================================
    // NEW – SHOW FEES EARNED PER EVENT
    // =====================================================================
    private static void showEventFees(List<Event> events) {
        System.out.println("\n===== FEES EARNED PER EVENT =====");
        for (Event e : events) {
            double cFee = e.getConvenienceFeeCollected();
            double sFee = e.getServiceFeeCollected();
            double chFee = e.getCharityFeeCollected();
            double total = cFee + sFee + chFee;

            System.out.println("\nEvent ID: " + e.getEventId() + " | " + e.getName());
            System.out.printf("  Convenience Fees: $%.2f%n", cFee);
            System.out.printf("  Service Fees:     $%.2f%n", sFee);
            System.out.printf("  Charity Fees:     $%.2f%n", chFee);
            System.out.printf("  TOTAL FEES:       $%.2f%n", total);
        }
        Logger.log("Admin viewed fees earned per event.");
    }

    // =====================================================================
    // NEW – SHOW TOTAL FEES ACROSS ALL EVENTS
    // =====================================================================
    private static void showTotalCompanyFees(List<Event> events) {
        double totalConvenience = 0;
        double totalService = 0;
        double totalCharity = 0;

        for (Event e : events) {
            totalConvenience += e.getConvenienceFeeCollected();
            totalService += e.getServiceFeeCollected();
            totalCharity += e.getCharityFeeCollected();
        }

        double total = totalConvenience + totalService + totalCharity;

        System.out.println("\n===== TOTAL FEES (ALL EVENTS) =====");
        System.out.printf("Total Convenience Fees: $%.2f%n", totalConvenience);
        System.out.printf("Total Service Fees:     $%.2f%n", totalService);
        System.out.printf("Total Charity Fees:     $%.2f%n", totalCharity);
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL FEES COLLECTED:  $%.2f%n", total);

        Logger.log("Admin viewed TOTAL fees across all events.");
    }

    // =====================================================================
    // NEW – CANCEL EVENT (Refund everything)
    // =====================================================================
    private static void cancelEvent(List<Event> events, List<Customer> customers, List<Invoice> invoices) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter Event ID to cancel: ");
        int eventId;

        try {
            eventId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid event ID.");
            return;
        }

        Event target = null;
        for (Event e : events) {
            if (e.getEventId() == eventId) {
                target = e;
                break;
            }
        }

        if (target == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("\nCANCELLING EVENT: " + target.getName());
        Logger.log("Admin cancelled event: " + target.getName());

        // REFUND ALL CUSTOMERS
        List<Invoice> toRemove = new ArrayList<>();

        for (Invoice inv : invoices) {
            if (inv.getEvent().equals(target)) {

                Customer c = inv.getCustomer();

                // Refund ALL: ticket cost + fees
                double refundAmount = inv.getTotalAmount();

                c.addMoney(refundAmount);

                System.out.println("Refunded $" + String.format("%.2f", refundAmount)
                        + " to " + c.getUsername());

                toRemove.add(inv);
            }
        }

        invoices.removeAll(toRemove);

        // Remove event
        events.remove(target);

        System.out.println("\nEvent successfully cancelled and all customers refunded.");
    }

    // =====================================================================
    // ORIGINAL METHODS (Unchanged)
    // =====================================================================

    private static void showEventTax(List<Event> events) {
        System.out.println("\n===== TOTAL TAX COLLECTED PER EVENT =====");
        for (Event e : events) {
            System.out.println("Event ID: " + e.getEventId() + " - " + e.getName()
                    + " | Total Tax Collected: $" + String.format("%.2f", e.getTotalTaxCollected()));
        }
        Logger.log("Admin viewed total tax collected per event.");
    }

    private static void inquireById(List<Event> events) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the event: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }
        for (Event e : events) {
            if (e.getEventId() == id) {
                e.displayDetailedInfo();
                Logger.log("Admin inquired event by ID: " + id);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private static void inquireByName(List<Event> events) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the event: ");
        String name = scanner.nextLine().trim();
        for (Event e : events) {
            if (e.getName().equalsIgnoreCase(name)) {
                e.displayDetailedInfo();
                Logger.log("Admin inquired event by name: " + name);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private static void showAllEvents(List<Event> events) {
        System.out.println("\n===== ALL EVENTS =====");
        for (Event e : events) e.displaySummary();
        Logger.log("Admin viewed all events summary.");
    }

    private static void showAllCustomers(List<Customer> customers) {
        System.out.println("\n===== ALL CUSTOMERS =====");
        for (Customer c : customers) {
            System.out.println("[" + c.getId() + "] " + c.getFirstName() + " " + c.getLastName()
                    + " | Username: " + c.getUsername()
                    + " | Balance: $" + String.format("%.2f", c.getMoneyAvailable()));
        }
        Logger.log("Admin viewed all customers.");
    }
}


