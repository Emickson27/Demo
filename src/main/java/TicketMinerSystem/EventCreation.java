import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/*
 * EventCreation.java
 * Handles the workflow for creating new events via console input.
 * Interacts with EventFactory and VenueFactory to instantiate events and venues.
 * Validates user input for date, time, and pricing.
 * Generates unique event IDs based on existing events.
 * Logs event creation actions for auditing.
 * Supports multiple event types (Concert, Sport, Festival).
 * Provides a summary of the created event including ticket prices and seat allocation.
 */

    
public class EventCreation {

    private final List<Event> events;

    public EventCreation(List<Event> events) {
        this.events = events;
    }

    public void createEventFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== CREATE NEW EVENT =====");
        System.out.print("Enter event type (Concert, Sport, Festival): ");
        String eventType = scanner.nextLine().trim();

        EventFactory eventFactory = EventFactoryDecider.getFactory(eventType);
        if (eventFactory == null) {
            System.out.println("Invalid event type. Please try again.");
            return;
        }

        System.out.print("Enter event name: ");
        String name = scanner.nextLine().trim();

        // ===== Validate date =====
        String date;
        while (true) {
            System.out.print("Enter event date (MM/DD/YYYY): ");
            date = scanner.nextLine().trim();
            if (isValidDate(date)) break;
            System.out.println("Invalid date format. Try again.");
        }

        // ===== Validate time =====
        String time;
        while (true) {
            System.out.print("Enter event time (HH:MM AM/PM): ");
            time = scanner.nextLine().trim();
            if (time.matches("^(1[0-2]|0?[1-9]):[0-5][0-9]\\s?(AM|PM|am|pm)$")) break;
            System.out.println("Invalid time format. Example: 07:30 PM");
        }

        // ===== Venue selection =====
        System.out.println("\nSelect Venue:");
        System.out.println("1. Sun Bowl Stadium");
        System.out.println("2. Don Haskins Center");
        System.out.println("3. Magoffin Auditorium");
        System.out.println("4. San Jacinto Plaza");
        System.out.println("5. Centennial Plaza");
        System.out.println("6. Glory Field");
        System.out.println("7. Foster Stevens Center");
        System.out.print("Enter choice: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        String venueName;
        String venueType;
        int capacity;
        switch (choice) {
            case 1 -> { venueName = "Sun Bowl Stadium"; venueType = "stadium"; capacity = 58000; }
            case 2 -> { venueName = "Don Haskins Center"; venueType = "arena"; capacity = 12000; }
            case 3 -> { venueName = "Magoffin Auditorium"; venueType = "auditorium"; capacity = 1152; }
            case 4 -> { venueName = "San Jacinto Plaza"; venueType = "openair"; capacity = 15000; }
            case 5 -> { venueName = "Centennial Plaza"; venueType = "field"; capacity = 5000; }
            case 6 -> { venueName = "Glory Field"; venueType = "field"; capacity = 10000; }
            case 7 -> { venueName = "Foster Stevens Center"; venueType = "auditorium"; capacity = 8000; }
            default -> {
                System.out.println("Invalid venue selection.");
                return;
            }
        }

        VenueFactory venueFactory = VenueFactoryDecider.getFactory(venueType);
        Venue venue = venueFactory.createVenue(venueName, capacity);

        System.out.print("Include fireworks? (yes/no): ");
        boolean fireworks = scanner.nextLine().trim().equalsIgnoreCase("yes");

        // ===== General Admission price (max $4000) =====
        double generalPrice;
        while (true) {
            System.out.print("Enter General Admission Price ($4000 max): ");
            try {
                generalPrice = Double.parseDouble(scanner.nextLine().trim());
                if (generalPrice > 0 && generalPrice <= 4000) break;
                System.out.println("Price must be between $1 and $4000.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        // ===== Compute other ticket prices =====
        double vipPrice = generalPrice * 5.0;
        double goldPrice = generalPrice * 3.0;
        double silverPrice = generalPrice * 2.5;
        double bronzePrice = generalPrice * 1.5;

        // ===== Compute seat allocation =====
        int vipSeats = (int) (capacity * 0.05);
        int goldSeats = (int) (capacity * 0.10);
        int silverSeats = (int) (capacity * 0.15);
        int bronzeSeats = (int) (capacity * 0.20);
        int generalSeats = (int) (capacity * 0.45);
        int reservedExtra = capacity - (vipSeats + goldSeats + silverSeats + bronzeSeats + generalSeats);

        // ===== Auto-generate ID based on existing events =====
        int eventId = computeNextEventId();

        Event event = eventFactory.createEvent(
                eventId, name, date, time,
                vipPrice, goldPrice, silverPrice, bronzePrice, generalPrice,
                fireworks, venue
        );

        events.add(event);

        // ===== Display summary =====
        System.out.println("\nEvent created successfully!");
        System.out.println("Event ID: " + eventId);
        System.out.println("Name: " + name);
        System.out.println("Venue: " + venueName + " | Capacity: " + capacity);
        System.out.println("Date: " + date + " " + time);
        System.out.println("Fireworks Included: " + (fireworks ? "Yes" : "No"));

        System.out.println("\n--- Ticket Prices ---");
        System.out.printf("VIP: $%.2f%n", vipPrice);
        System.out.printf("Gold: $%.2f%n", goldPrice);
        System.out.printf("Silver: $%.2f%n", silverPrice);
        System.out.printf("Bronze: $%.2f%n", bronzePrice);
        System.out.printf("General Admission: $%.2f%n", generalPrice);

        System.out.println("\n--- Seats Allocation ---");
        System.out.println("VIP: " + vipSeats);
        System.out.println("Gold: " + goldSeats);
        System.out.println("Silver: " + silverSeats);
        System.out.println("Bronze: " + bronzeSeats);
        System.out.println("General Admission: " + generalSeats);
        System.out.println("Reserved Extra: " + reservedExtra);

        Logger.log("Admin created event: " + name + " (" + eventType + 
            ") ID=" + eventId + " | Capacity=" + capacity + " | GA Price=" + generalPrice);
    }

    // ===== Helpers =====

    private static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private int computeNextEventId() {
        int maxId = 0;
        for (Event e : events) {
            if (e.getEventId() > maxId) {
                maxId = e.getEventId();
            }
        }
        return maxId + 1;
    }
}





