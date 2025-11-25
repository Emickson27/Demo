package src.test.java.TicketMinerSystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    void testCalculateTotalForMember() {
        Invoice invoice = new Invoice();
        double result = invoice.calculateTotal(10.0, 5, true);
        assertEquals(45.0, result, 0.01); // 10*5 = 50, minus 10% = 45
    }

    @Test
    void testCalculateTotalForNonMember() {
        Invoice invoice = new Invoice();
        double result = invoice.calculateTotal(10.0, 5, false);
        assertEquals(50.0, result, 0.01);
    }

    @Test
    void testZeroBasePrice() {
        Invoice invoice = new Invoice();
        double result = invoice.calculateTotal(0.0, 5, false);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    void testNegativeInputs() {
        Invoice invoice = new Invoice();
        assertThrows(IllegalArgumentException.class, () -> {
            invoice.calculateTotal(-10, 3, false);
        });
    }
}

