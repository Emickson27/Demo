/*
 * EventFactoryDecider.java
 * Decides which EventFactory to use based on the event type.
 * Returns the appropriate factory instance for creating events.
 */


public class EventFactoryDecider {
    /*
     * Returns the appropriate EventFactory based on event type
     * @param eventType Type of event (e.g. "concert", "sport", "festival")
     * @return EventFactory instance corresponding to the event type
     */

     
    public static EventFactory getFactory(String eventType) {
        switch (eventType.toLowerCase()) {
            case "concert":
                return new ConcertFactory();
            case "sport":
                return new SportFactory();
            case "festival":
                return new FestivalFactory();
            default:
                return null;
        }
    }
}