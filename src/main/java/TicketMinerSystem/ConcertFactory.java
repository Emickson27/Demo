/*
 * ConcertFactory.java
 * Factory class to create Concert event instances.
 * Uses the Factory Method design pattern.
 * Creates Concert objects with specified attributes.
 * Inherits from EventFactory.
 * Implements the createEvent method to instantiate Concerts.
 * Returns a new Concert object with provided parameters.
 * Parameters include event details and venue.
 * Facilitates easy creation of Concert events in the system.
 * Promotes code reusability and maintainability.
 * Part of the TicketMinerSystem package.
 */

public class ConcertFactory extends EventFactory {
    @Override
    public Event createEvent(int eventId, String name, String date, String time,
                             double vipPrice, double goldPrice, double silverPrice,
                             double bronzePrice, double generalPrice,
                             boolean isFireworksIncluded, Venue venue) {
        return new Concert(eventId, name, date, time,
                           vipPrice, goldPrice, silverPrice, bronzePrice, generalPrice,
                           isFireworksIncluded, venue);
    }
}


