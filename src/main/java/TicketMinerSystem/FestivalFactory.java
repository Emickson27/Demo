/*
 * FestivalFactory.java
 * Factory class to create Festival event instances.
 * Uses the Factory Method design pattern.
 * Creates Festival objects with specified attributes.
 * Inherits from EventFactory.
 * Implements the createEvent method to instantiate Festivals.
 * Returns a new Festival object with provided parameters.
 * Parameters include event details and venue.
 * Facilitates easy creation of Festival events in the system.
 */

public class FestivalFactory extends EventFactory {
    /*
     * Creates and returns a new Festival instance
     * @param eventId Unique identifier for the event
     * @param name Name of the festival
     * @param date Date of the festival
     * @param time Time of the festival
     * @param vipPrice Price for VIP tickets
     * @param goldPrice Price for Gold tickets
     * @param silverPrice Price for Silver tickets
     * @param bronzePrice Price for Bronze tickets
     * @param generalPrice Price for General tickets
     * @param isFireworksIncluded Whether fireworks are included
     * @param venue Venue of the festival
     * @return Event instance of Festival
     */

     
    public Event createEvent(
        int eventId,
        String name,
        String date,
        String time,
        double vipPrice,
        double goldPrice,
        double silverPrice,
        double bronzePrice,
        double generalPrice,
        boolean isFireworksIncluded,
        Venue venue
    ) {
        return new Festival(eventId, name, date, time,
                vipPrice, goldPrice, silverPrice, bronzePrice, generalPrice, isFireworksIncluded, venue);
    }
}
