/*
 * StadiumFactory.java
 * Factory class to create Stadium venue instances.
 * Implements the factory method defined in VenueFactory.
 */


public class StadiumFactory extends VenueFactory {
    @Override
    public Venue createVenue(String name, int capacity) {
        return new Stadium(name, capacity);
    }

}
