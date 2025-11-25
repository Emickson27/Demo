/*
 * ArenaFactory.java
 * Factory class to create Arena venue instances.
 */


public class ArenaFactory extends VenueFactory {
    

    /*
     * Creates and returns a new Arena instance
     * @param name Name of the arena
     * @param capacity Seating capacity of the arena
     * @return Venue instance of Arena
     */
    
     
     @Override
    public Venue createVenue(String name, int capacity) {
        return new Arena(name, capacity);
    }
}
