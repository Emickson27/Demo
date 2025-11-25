import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventReceipt {

    private static final String RECEIPT_FOLDER = "customer_receipts";

    // ORIGINAL METHOD (kept 100% untouched)
    public static void writeCustomerReceipt(String customerName, Event event,
                                            String ticketType, int numTickets,
                                            double totalPrice, double tax) {

        double grandTotal = totalPrice + tax;
        String confirmation = "CNF-" + System.currentTimeMillis();

        File folder = new File(RECEIPT_FOLDER);
        if (!folder.exists()) folder.mkdir();

        StringBuilder receipt = new StringBuilder();
        receipt.append("=== Customer Receipt Summary ===\n");
        receipt.append("Customer: ").append(customerName).append("\n");
        receipt.append("Event: ").append(event.getName()).append("\n");
        receipt.append("Date: ").append(event.getDate()).append("\n");
        receipt.append("Ticket Type: ").append(ticketType).append("\n");
        receipt.append("Quantity: ").append(numTickets).append("\n");
        receipt.append("Subtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
        receipt.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        receipt.append("Total: $").append(String.format("%.2f", grandTotal)).append("\n");
        receipt.append("Confirmation #: ").append(confirmation).append("\n");
        receipt.append("---------------------------------\n");
        receipt.append("Thank you for your purchase!\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        String safeName = customerName.replaceAll("\\s+", "_");
        String filePath = RECEIPT_FOLDER + "/Receipt_" + safeName + "_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(receipt.toString());
            System.out.println("Receipt generated successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing receipt: " + e.getMessage());
        }
    }

    // =====================================================================
    // NEW OVERLOADED METHOD — FULL BREAKDOWN (do not delete original)
    // =====================================================================
    public static void writeCustomerReceiptFull(String customerName, Event event,
                                                String ticketType, int numTickets,
                                                double baseSubtotal,
                                                double discountAmount,
                                                double convenienceFee,
                                                double serviceFee,
                                                double charityFee,
                                                double subtotalAfterFees,
                                                double tax,
                                                double total) {

        String confirmation = "CNF-" + System.currentTimeMillis();

        File folder = new File(RECEIPT_FOLDER);
        if (!folder.exists()) folder.mkdir();

        StringBuilder r = new StringBuilder();
        r.append("=== Customer Receipt Summary ===\n");
        r.append("Customer: ").append(customerName).append("\n");
        r.append("Event: ").append(event.getName()).append("\n");
        r.append("Date: ").append(event.getDate()).append("\n");
        r.append("Ticket Type: ").append(ticketType).append("\n");
        r.append("Quantity: ").append(numTickets).append("\n\n");

        r.append("--- COST BREAKDOWN ---\n");
        r.append(String.format("Base Subtotal:       $%.2f%n", baseSubtotal));
        r.append(String.format("Member Discount:     -$%.2f%n", discountAmount));
        r.append(String.format("Convenience Fee:     $%.2f%n", convenienceFee));
        r.append(String.format("Service Fee:         $%.2f%n", serviceFee));
        r.append(String.format("Charity Fee:         $%.2f%n", charityFee));
        r.append("---------------------------------\n");
        r.append(String.format("Subtotal After Fees: $%.2f%n", subtotalAfterFees));
        r.append(String.format("Tax:                 $%.2f%n", tax));
        r.append("---------------------------------\n");
        r.append(String.format("TOTAL:               $%.2f%n", total));

        r.append("Confirmation #: ").append(confirmation).append("\n");
        r.append("---------------------------------\n");
        r.append("Thank you for your purchase!\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        String safeName = customerName.replaceAll("\\s+", "_");
        String filePath = RECEIPT_FOLDER + "/Receipt_" + safeName + "_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(r.toString());
            System.out.println("Receipt generated successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing receipt: " + e.getMessage());
        }
    }
}

