import java.util.List;

/*
 * AutoPurchaseProcessor.java
 * UPDATED TO SUPPORT FEES:
 * - Convenience Fee
 * - Service Fee
 * - Charity Fee
 * 
 * Original logic preserved.
 */

public class AutoPurchaseProcessor {

    private final List<Event> events;
    private final List<Customer> customers;
    private final List<Invoice> invoices;

    public AutoPurchaseProcessor(List<Event> events, List<Customer> customers, List<Invoice> invoices) {
        this.events = events;
        this.customers = customers;
        this.invoices = invoices;
    }

    public boolean processAutoPurchase(String username, int eventId, String ticketType, int quantity) {

        // ===== Find customer =====
        Customer customer = findCustomer(username);
        if (customer == null) {
            System.out.println(" ERROR: Customer '" + username + "' not found.");
            Logger.log("[AUTO] ERROR: Customer not found: " + username);
            return false;
        }

        // ===== Find event =====
        Event event = findEvent(eventId);
        if (event == null) {
            System.out.println(" ERROR: Event with ID " + eventId + " not found.");
            Logger.log("[AUTO] ERROR: Event not found: ID " + eventId);
            return false;
        }

        // ===== Validate ticket type =====
        double price;
        try {
            price = event.getPriceByType(ticketType);
        } catch (IllegalArgumentException ex) {
            System.out.println(" ERROR: Invalid ticket type '" + ticketType + "' for event ID " + eventId);
            Logger.log("[AUTO] ERROR: Invalid ticket type " + ticketType + " for event " + eventId);
            return false;
        }

        // ===== Validate seat availability =====
        if (!event.hasAvailableSeats(ticketType, quantity)) {
            System.out.println(" ERROR: Not enough " + ticketType + " seats available for event ID " + eventId);
            Logger.log("[AUTO] ERROR: Insufficient " + ticketType + " seats for event " + eventId);
            return false;
        }

        // ======================================================
        // ORIGINAL SUBTOTAL AND DISCOUNT
        // ======================================================
        double baseSubtotal = PricingCalculator.calculateSubtotal(price, quantity);
        double discountedSubtotal =
                PricingCalculator.applyMemberDiscount(baseSubtotal, customer.isTicketMinerMemebership());
        double discountAmount = baseSubtotal - discountedSubtotal;

        // ======================================================
        // NEW FEES (Required by the assignment)
        // ======================================================
        double convenienceFee = PricingCalculator.calculateConvenienceFee();
        double serviceFee = PricingCalculator.calculateServiceFee(price, quantity);
        double charityFee = PricingCalculator.calculateCharityFee(price, quantity);

        // Add fees to the event (tracking for admin)
        event.addConvenienceFee(convenienceFee);
        event.addServiceFee(serviceFee);
        event.addCharityFee(charityFee);

        // Subtotal after adding fees
        double subtotalAfterFees = discountedSubtotal + convenienceFee + serviceFee + charityFee;

        // Compute tax on subtotal + fees
        double tax = PricingCalculator.calculateTax(subtotalAfterFees);

        // Final customer total
        double total = PricingCalculator.calculateTotal(subtotalAfterFees, tax);

        // ======================================================
        // Check if customer can afford it
        // ======================================================
        if (!customer.canAfford(total)) {
            System.out.println(" ERROR: " + username + " cannot afford this purchase. Needed $" + total);
            Logger.log("[AUTO] ERROR: Insufficient funds for " + username + " (needs " + total + ")");
            return false;
        }

        // ======================================================
        // Apply transaction
        // ======================================================
        customer.deductMoney(total);

        if (customer.isTicketMinerMemebership()) {
            customer.addToTotalSaved(discountAmount);
        }

        customer.setConcertsPurchased(customer.getConcertsPurchased() + quantity);
        event.sellSeatsByType(ticketType, quantity);

        // ======================================================
        // Create invoice with FEES included
        // ======================================================
        Invoice invoice = new Invoice(
        customer,
        event,
        ticketType,
        quantity,
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

        // ======================================================
        // Log success
        // ======================================================
        System.out.println(" SUCCESS: " + username + " auto-purchased " + quantity + " "
                + ticketType.toUpperCase() + " tickets for event: " + event.getName()
                + " | Total: $" + String.format("%.2f", total));

        Logger.log("[AUTO] SUCCESS: " + username + " bought " + quantity + " "
                + ticketType + " tickets for " + event.getName()
                + " ($" + String.format("%.2f", total) + ")");

        return true;
    }

    // ===== Helpers =====

    private Customer findCustomer(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username)) return c;
        }
        return null;
    }

    private Event findEvent(int id) {
        for (Event e : events) {
            if (e.getEventId() == id) return e;
        }
        return null;
    }

    // ===== Batch Runner =====
    public void processAllAutoRequests(List<AutoRequest> requests) {
        int success = 0, failed = 0;
        for (AutoRequest r : requests) {
            boolean ok = processAutoPurchase(r.username, r.eventId, r.ticketType, r.quantity);
            if (ok) success++; else failed++;
        }

        System.out.println("\n=== AUTO PURCHASE SUMMARY ===");
        System.out.println("Successful transactions: " + success);
        System.out.println("Failed transactions: " + failed);
        System.out.println("=============================\n");

        Logger.log("[AUTO] Finished processing auto purchases. Success=" + success + ", Failed=" + failed);
    }
}


