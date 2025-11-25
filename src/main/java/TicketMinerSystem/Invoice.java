

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Invoice.java
 * UPDATED TO INCLUDE:
 *  - Convenience Fee
 *  - Service Fee
 *  - Charity Fee
 * 
 * Original structure preserved.
 */

public class Invoice {

    private static int counter = 1;
    private int invoiceId;
    private Customer customer;
    private Event event;
    private String ticketType;
    private int quantity;

    private double baseSubtotal;
    private double discountAmount;
    private double convenienceFee;
    private double serviceFee;
    private double charityFee;
    private double subtotalAfterFees;
    private double tax;
    private double totalAmount;

    private LocalDateTime timestamp;

    public Invoice(
            Customer customer, Event event, String ticketType, int quantity,
            double baseSubtotal, double discountAmount,
            double convenienceFee, double serviceFee, double charityFee,
            double subtotalAfterFees, double tax, double totalAmount
    ) {
        this.invoiceId = counter++;
        this.customer = customer;
        this.event = event;
        this.ticketType = ticketType;
        this.quantity = quantity;

        this.baseSubtotal = baseSubtotal;
        this.discountAmount = discountAmount;
        this.convenienceFee = convenienceFee;
        this.serviceFee = serviceFee;
        this.charityFee = charityFee;
        this.subtotalAfterFees = subtotalAfterFees;
        this.tax = tax;
        this.totalAmount = totalAmount;

        this.timestamp = LocalDateTime.now();
    }

        // ===== GETTERS FOR ALL FIELDS =====
    public Customer getCustomer() { return customer; }
    public Event getEvent() { return event; }

    public String getTicketType() { return ticketType; }
    public int getQuantity() { return quantity; }

    public double getBaseSubtotal() { return baseSubtotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getConvenienceFee() { return convenienceFee; }
    public double getServiceFee() { return serviceFee; }
    public double getCharityFee() { return charityFee; }
    public double getSubtotalAfterFees() { return subtotalAfterFees; }
    public double getTax() { return tax; }
    public double getTotalAmount() { return totalAmount; }



    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        return "\n=== Invoice ===\n" +
                "Invoice ID: " + invoiceId + "\n" +
                "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Member Status: " + (customer.isTicketMinerMemebership() ? "Member" : "Non-Member") + "\n" +
                "Event: " + event.getName() + "\n" +
                "Ticket Type: " + ticketType + "\n" +
                "Quantity: " + quantity + "\n\n" +

                "--- COST BREAKDOWN ---\n" +
                String.format("Base Subtotal:      $%.2f%n", baseSubtotal) +
                String.format("Member Discount:    -$%.2f%n", discountAmount) +
                String.format("Convenience Fee:    $%.2f%n", convenienceFee) +
                String.format("Service Fee:        $%.2f%n", serviceFee) +
                String.format("Charity Fee:        $%.2f%n", charityFee) +
                "---------------------------\n" +
                String.format("Subtotal After Fees: $%.2f%n", subtotalAfterFees) +
                String.format("Tax:                 $%.2f%n", tax) +
                "---------------------------\n" +
                String.format("Total Amount:        $%.2f%n", totalAmount) +
                "Date: " + timestamp.format(formatter) + "\n" +
                "---------------------------";
    }
}