/*
 * VenueFactory.java
 * Abstract factory class for creating Venue instances.
 * Defines the factory method to be implemented by concrete venue factories.
 */

 
public abstract class VenueFactory {
    public abstract Venue createVenue(String name, int capacity);
}
