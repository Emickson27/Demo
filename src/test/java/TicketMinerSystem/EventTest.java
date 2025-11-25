package src.test.java.TicketMinerSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event concert;

    @BeforeEach
    void setup() {
        concert = new Event(1, "Summer Bash", "07/20/2025", "9:00 PM", "Sun Bowl", 100);
        concert.addTicketType("Gold", 30.0, 5);
    }

    @Test
    void testValidSeatReservation() {
        assertTrue(concert.reserveSeats("Gold", 3));
        assertEquals(2, concert.getAvailableSeats("Gold"));
    }

    @Test
    void testOverReservation() {
        assertFalse(concert.reserveSeats("Gold", 10));
    }

    @Test
    void testInvalidTicketType() {
        assertFalse(concert.reserveSeats("Diamond", 2));
    }

    @Test
    void testNegativeQuantity() {
        assertFalse(concert.reserveSeats("Gold", -1));
    }

    @Test
    void testReleaseSeats() {
        concert.reserveSeats("Gold", 3);
        concert.releaseSeats("Gold", 2);
        assertEquals(4, concert.getAvailableSeats("Gold"));
    }
}

