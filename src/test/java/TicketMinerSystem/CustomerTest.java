package src.test.java.TicketMinerSystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void testCanAffordAndDeductMoney() {
        Customer c = new Customer(1, "Alice", "Johnson", 200.0, 0, false, "aliceJ", "pass123");

        assertTrue(c.canAfford(150.0), "Customer should afford 150 if balance is 200");
        c.deductMoney(150.0);
        assertEquals(50.0, c.getMoneyAvailable(), 0.001, "After deduction, remaining should be 50");
    }

    @Test
    void testDeductMoneyThrowsWhenInsufficientFunds() {
        Customer c = new Customer(2, "Bob", "Smith", 50.0, 0, false, "bobS", "pass123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            c.deductMoney(100.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testAddToTotalSaved() {
        Customer c = new Customer(3, "Maria", "Lopez", 100.0, 0, true, "mariaL", "123");
        c.addToTotalSaved(25.5);
        assertEquals(25.5, c.getTotalSaved(), 0.001);
        c.addToTotalSaved(4.5);
        assertEquals(30.0, c.getTotalSaved(), 0.001, "Total saved should accumulate correctly");
    }
}

