
/*
 * PricingCalculator.java
 * Provides static methods to calculate pricing details for ticket purchases.
 * Includes methods to calculate subtotal, apply member discounts, calculate tax, and total amount.
 * Uses Texas state tax rate and a fixed member discount rate.
 * 
 * UPDATED TO INCLUDE:
 *  - $2.50 convenience fee per transaction
 *  - 0.5% service fee per ticket (ticketCost * 0.005)
 *  - 0.75% charity fee per ticket (ticketCost * 0.0075)
 * 
 * Original behavior preserved.
 */

public class PricingCalculator {

    private static final double TEXAS_TAX_RATE = 0.0825;
    private static final double MEMBER_DISCOUNT_RATE = 0.10;

    // ============================================================
    // ORIGINAL METHODS (UNCHANGED)
    // ============================================================

    public static double calculateSubtotal(double ticketPrice, int quantity) {
        return ticketPrice * quantity;
    }

    public static double applyMemberDiscount(double subtotal, boolean isMember) {
        return isMember ? subtotal * (1 - MEMBER_DISCOUNT_RATE) : subtotal;
    }

    // floor-to-cent rounding preserved
    public static double calculateTax(double subtotal) {
        return Math.floor(subtotal * TEXAS_TAX_RATE * 100) / 100.0;
    }

    public static double calculateTotal(double subtotal, double tax) {
        return Math.floor((subtotal + tax) * 100) / 100.0;
    }


    // ============================================================
    // NEW — REQUIRED FEES FOR ASSIGNMENT
    // ============================================================

    /**
     * $2.50 convenience fee per transaction
     */
    public static double calculateConvenienceFee() {
        return 2.50;
    }

    /**
     * 0.5% service fee applied to TOTAL ticket cost
     */
    public static double calculateServiceFee(double ticketPrice, int quantity) {
        double cost = ticketPrice * quantity;
        return cost * 0.005; // 0.5%
    }

    /**
     * 0.75% charity fee applied to TOTAL ticket cost
     */
    public static double calculateCharityFee(double ticketPrice, int quantity) {
        double cost = ticketPrice * quantity;
        return cost * 0.0075; // 0.75%
    }

    /**
     * Returns ALL fees combined:
     * Convenience + Service + Charity
     */
    public static double calculateTotalFees(double ticketPrice, int quantity) {
        double convenience = calculateConvenienceFee();
        double service = calculateServiceFee(ticketPrice, quantity);
        double charity = calculateCharityFee(ticketPrice, quantity);

        return convenience + service + charity;
    }
}



