import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * CustomerMenu.java
 * UPDATED TO INCLUDE:
 * - Service Fees
 * - Convenience Fee
 * - Charity Fee
 * - Cancel Ticket Purchase Feature
 */

public class CustomerMenu {
    private final List<Event> events;
    private final List<Customer> customers;
    private final List<Invoice> invoices;
    private final Scanner scanner;

    public CustomerMenu(List<Event> events, List<Customer> customers, List<Invoice> invoices) {
        this.events = events;
        this.customers = customers;
        this.invoices = invoices;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(Customer customer) {
        String choice;
        do {
            System.out.println("\n=========================");
            System.out.println("===== CUSTOMER MENU =====");
            System.out.println("=========================");
            System.out.println("\nWelcome, " + customer.getFirstName() + "!");
            System.out.printf("Your current balance is: $%.2f%n", customer.getMoneyAvailable());
            System.out.println("1. View Events");
            System.out.println("2. Purchase Ticket");
            System.out.println("3. View My Invoices");
            System.out.println("4. Cancel Ticket Purchase");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewEvents();
                case "2" -> purchaseTicket(customer);
                case "3" -> viewInvoices(customer);
                case "4" -> cancelTicket(customer);
                case "5" -> {
                    System.out.println("Logging out...");
                    Logger.log("Customer logged out: " + customer.getUsername());
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (!"5".equals(choice));
    }

    private void viewEvents() {
        System.out.println("\n===================");
        System.out.println("====== EVENTS =====");
        System.out.println("===================\n");
        for (Event e : events) e.displaySummary();
        Logger.log("Customer viewed events list.");
    }

    private void viewInvoices(Customer c) {
        System.out.println("\n===== YOUR INVOICES =====");
        boolean found = false;

        for (Invoice inv : invoices) {
            if (inv.getCustomer().equals(c)) {
                System.out.println(inv);
                found = true;
            }
        }

        if (!found) System.out.println("No invoices found.");
        Logger.log("Customer viewed invoices: " + c.getUsername());
    }

    private void cancelTicket(Customer customer) {
        System.out.println("\n===== CANCEL A TICKET PURCHASE =====");

        // Gather this customer's invoices
        List<Invoice> myInvoices = new ArrayList<>();
        for (Invoice inv : invoices) {
            if (inv.getCustomer().equals(customer)) {
                myInvoices.add(inv);
            }
        }

        if (myInvoices.isEmpty()) {
            System.out.println("You have no purchases to cancel.");
            return;
        }

        // Display numbered list
        for (int i = 0; i < myInvoices.size(); i++) {
            Invoice inv = myInvoices.get(i);
            System.out.println((i + 1) + ". Event: " + inv.getEvent().getName()
                    + " | Tickets: " + inv.getQuantity()
                    + " | Type: " + inv.getTicketType()
                    + " | Paid: $" + String.format("%.2f", inv.getTotalAmount()));
        }

        System.out.print("\nSelect which purchase to cancel (number): ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid selection.");
            return;
        }

        if (choice < 1 || choice > myInvoices.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Invoice inv = myInvoices.get(choice - 1);
        Event event = inv.getEvent();

        // Refund ONLY ticket base cost (NOT fees)
        double ticketCostOnly =
                PricingCalculator.calculateSubtotal(
                        event.getPriceByType(inv.getTicketType()),
                        inv.getQuantity()
                );

        // Return money
        customer.addMoney(ticketCostOnly);

        // Return seats
        event.sellSeatsByType(inv.getTicketType(), -inv.getQuantity());

        // Remove invoice
        invoices.remove(inv);

        System.out.println("\nPurchase canceled successfully!");
        System.out.printf("Refunded (ticket price only): $%.2f%n", ticketCostOnly);

        Logger.log("Customer " + customer.getUsername()
                + " canceled purchase for event " + event.getName()
                + " | Refunded $" + ticketCostOnly);
    }

    private void purchaseTicket(Customer customer) {
        System.out.println("\n===============================");
        System.out.println("========= PURCHASE TICKET =====");
        System.out.println("===============================");

        for (Event e : events) e.displaySummary();

        System.out.print("\nEnter Event ID to purchase: ");
        final int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric ID.");
            return;
        }

        Event event = null;
        for (Event e : events) {
            if (e.getEventId() == id) { event = e; break; }
        }
        if (event == null) {
            System.out.println("Event not found. Please check the ID and try again.");
            return;
        }

        System.out.println("\n===============================");
        System.out.println("Ticket Options for " + event.getName());
        System.out.println("===============================");
        System.out.printf("%-10s | %-10s%n", "Type", "Price ($)");
        System.out.println("-------------------------------");
        System.out.printf("%-10s | %-10.2f%n", "VIP", event.getVipPrice());
        System.out.printf("%-10s | %-10.2f%n", "Gold", event.getGoldPrice());
        System.out.printf("%-10s | %-10.2f%n", "Silver", event.getSilverPrice());
        System.out.printf("%-10s | %-10.2f%n", "Bronze", event.getBronzePrice());
        System.out.printf("%-10s | %-10.2f%n", "General", event.getGeneralPrice());
        System.out.println("-------------------------------");

        System.out.print("\nEnter ticket type (VIP, Gold, Silver, Bronze, General): ");
        String type = scanner.nextLine().trim();

        final double price;
        try {
            price = event.getPriceByType(type);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid ticket type.");
            return;
        }

        System.out.print("Enter quantity (1-6): ");
        final int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input.");
            return;
        }
        if (qty < 1 || qty > 6) {
            System.out.println("You can only purchase between 1 and 6 tickets.");
            return;
        }

        // Subtotal and discounts
        double baseSubtotal = PricingCalculator.calculateSubtotal(price, qty);
        double discountedSubtotal =
                PricingCalculator.applyMemberDiscount(baseSubtotal, customer.isTicketMinerMemebership());
        double discountAmount = baseSubtotal - discountedSubtotal;

        // FEES
        double convenienceFee = PricingCalculator.calculateConvenienceFee();
        double serviceFee = PricingCalculator.calculateServiceFee(price, qty);
        double charityFee = PricingCalculator.calculateCharityFee(price, qty);

        // Add fees to event
        event.addConvenienceFee(convenienceFee);
        event.addServiceFee(serviceFee);
        event.addCharityFee(charityFee);

        double subtotalAfterFees = discountedSubtotal + convenienceFee + serviceFee + charityFee;
        double tax = PricingCalculator.calculateTax(subtotalAfterFees);
        double total = PricingCalculator.calculateTotal(subtotalAfterFees, tax);

        if (!customer.canAfford(total)) {
            System.out.println("Insufficient funds.");
            return;
        }

        customer.deductMoney(total);

        if (customer.isTicketMinerMemebership()) {
            customer.addToTotalSaved(discountAmount);
        }

        event.sellSeatsByType(type, qty);
        customer.setConcertsPurchased(customer.getConcertsPurchased() + qty);

        Invoice invoice = new Invoice(
        customer,
        event,
        type,
        qty,
        baseSubtotal,
        discountAmount,
        convenienceFee,
        serviceFee,
        charityFee,
        subtotalAfterFees,
        tax,
        total
);


        invoices.add(invoice);

        System.out.println("\nPurchase successful!");
        System.out.println(invoice);

        EventReceipt.writeCustomerReceiptFull(
                customer.getFirstName() + " " + customer.getLastName(),
                event,
                type,
                qty,
                baseSubtotal,
                discountAmount,
                convenienceFee,
                serviceFee,
                charityFee,
                subtotalAfterFees,
                tax,
                total
        );

    }
}

