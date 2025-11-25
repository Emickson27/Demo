/*
 * AuditoriumFactory.java
 * Factory class to create Auditorium venue instances.
 */


public class AuditoriumFactory extends VenueFactory {


    /*
     * Creates and returns a new Auditorium instance
     * @param name Name of the auditorium
     * @param capacity Seating capacity of the auditorium
     * @return Venue instance of Auditorium
     */

     
    @Override
    public Venue createVenue(String name, int capacity) {
        return new Auditorium(name, capacity);
    }   
    
}
