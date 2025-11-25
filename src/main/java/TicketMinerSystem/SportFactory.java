/*
 * SportFactory.java
 * Factory class to create Sport event instances.
 * Implements the factory method defined in EventFactory.
 */

 
public class SportFactory extends EventFactory {
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
        return new Sport(eventId, name, date, time,
                vipPrice, goldPrice, silverPrice, bronzePrice, generalPrice, isFireworksIncluded, venue);
    }
}