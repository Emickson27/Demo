/*
 * OpenAirFactory.java
 * Factory class to create OpenAir venue instances.
 * Implements the factory method defined in VenueFactory.
 */

 
public class OpenAirFactory extends VenueFactory {
    @Override
    public Venue createVenue(String name, int capacity) {
        return new OpenAir(name, capacity);
    }
    

}