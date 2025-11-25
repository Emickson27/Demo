/*
 * OutdoorFactory.java
 * Factory class to create Outdoor venue instances.
 * Implements the factory method defined in VenueFactory.
 */


public class OutdoorFactory extends VenueFactory {
    @Override
    public Venue createVenue(String name, int capacity) {
        return new Outdoor(name, capacity);
    }
    

}
