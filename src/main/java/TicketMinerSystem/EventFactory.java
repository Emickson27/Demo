/*
 * EventFactory.java
 * Abstract factory class to create Event instances.
 * Defines the factory method to be implemented by subclasses.
 */

public abstract class EventFactory {

    // Factory Method - to be implemented by subclasses
    /*
     * Creates and returns a new Event instance
     * @param eventId Unique identifier for the event
     * @param name Name of the event
     * @param date Date of the event
     * @param time Time of the event
     * @param vipPrice Price of VIP tickets
     * @param goldPrice Price of Gold tickets
     * @param silverPrice Price of Silver tickets
     * @param bronzePrice Price of Bronze tickets
     * @param generalPrice Price of General tickets
     * @param isFireworksIncluded Whether fireworks are included
     * @param venue Venue of the event
     * @return Event instance
     */
    public abstract Event createEvent(
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
    );
}
