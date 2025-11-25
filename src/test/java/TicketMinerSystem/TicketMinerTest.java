package src.test.java.TicketMinerSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketMinerTest {

    private TicketMiner ticketMiner;
    private Customer validCustomer;
    private Event concertEvent;

    @BeforeEach
    void setUp() {
        ticketMiner = new TicketMiner();
        validCustomer = new Customer("user123", "Kevin", "Munoz", 100.0);
        concertEvent = new Event(1, "Rock Fest", "12/12/2025", "8:00 PM", "Sun Bowl Stadium", 500);
        concertEvent.addTicketType("VIP", 50.0, 10);
        ticketMiner.addCustomer(validCustomer);
        ticketMiner.addEvent(concertEvent);
    }

    @Test
    void testValidPurchase() {
        boolean result = ticketMiner.purchaseTicket("user123", 1, "VIP", 2);
        assertTrue(result);
        assertEquals(0, concertEvent.getAvailableSeats("VIP"));
    }

    @Test
    void testInvalidUserId() {
        boolean result = ticketMiner.purchaseTicket("fakeUser", 1, "VIP", 1);
        assertFalse(result);
    }

    @Test
    void testSoldOutEvent() {
        concertEvent.reserveSeats("VIP", 10); // all reserved
        boolean result = ticketMiner.purchaseTicket("user123", 1, "VIP", 1);
        assertFalse(result);
    }

    @Test
    void testNegativeQuantity() {
        boolean result = ticketMiner.purchaseTicket("user123", 1, "VIP", -3);
        assertFalse(result);
    }

    @Test
    void testInvalidTicketType() {
        boolean result = ticketMiner.purchaseTicket("user123", 1, "PLATINUM", 1);
        assertFalse(result);
    }
}

