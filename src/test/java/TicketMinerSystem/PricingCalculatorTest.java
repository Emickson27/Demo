package src.test.java.TicketMinerSystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PricingCalculatorTest {

    @Test
    void testCalculateSubtotal() {
        double subtotal = PricingCalculator.calculateSubtotal(100.0, 3);
        assertEquals(300.0, subtotal, 0.001, "Subtotal should be price * quantity");
    }

    @Test
    void testApplyMemberDiscount() {
        double discounted = PricingCalculator.applyMemberDiscount(200.0, true);
        assertEquals(180.0, discounted, 0.001, "Member discount of 10% should apply correctly");

        double nonMember = PricingCalculator.applyMemberDiscount(200.0, false);
        assertEquals(200.0, nonMember, 0.001, "Non-members should not receive a discount");
    }

    @Test
    void testCalculateTax() {
        double tax = PricingCalculator.calculateTax(100.0);
        assertEquals(8.25, tax, 0.001, "Tax should be 8.25% of subtotal");
    }

    @Test
    void testCalculateTotal() {
        double total = PricingCalculator.calculateTotal(100.0, 8.25);
        assertEquals(108.25, total, 0.001, "Total should equal subtotal + tax");
    }
}


