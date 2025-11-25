package src.test.java.TicketMinerSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class AutoPurchaseProcessorTest {

    private AutoPurchaseProcessor processor;
    private List<Event> events;
    private List<Customer> customers;
    private List<Invoice> invoices;

    @BeforeEach
    void setup() {
        events = new ArrayList<>();
        customers = new ArrayList<>();
        invoices = new ArrayList<>();

        Event e = new Event(1, "Metal Night", "05/30/2025", "7:00 PM", "Don Haskins Center", 100);
        e.addTicketType("VIP", 80.0, 5);
        events.add(e);

        Customer c = new Customer("user001", "Kevin", "Munoz", 300.0);
        customers.add(c);

        processor = new AutoPurchaseProcessor(events, customers, invoices);
    }

    @Test
    void testValidAutoPurchase() {
        AutoRequest req = new AutoRequest("user001", 1, "VIP", 2);
        boolean success = processor.processAutoPurchase(req);
        assertTrue(success);
        assertEquals(1, invoices.size());
    }

    @Test
    void testSoldOutEvent() {
        AutoRequest req = new AutoRequest("user001", 1, "VIP", 10);
        boolean success = processor.processAutoPurchase(req);
        assertFalse(success);
    }

    @Test
    void testInvalidUser() {
        AutoRequest req = new AutoRequest("invalidUser", 1, "VIP", 1);
        boolean success = processor.processAutoPurchase(req);
        assertFalse(success);
    }

    @Test
    void testNegativeQuantity() {
        AutoRequest req = new AutoRequest("user001", 1, "VIP", -3);
        boolean success = processor.processAutoPurchase(req);
        assertFalse(success);
    }
}

