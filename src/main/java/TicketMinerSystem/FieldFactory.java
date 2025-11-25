/*
 * FieldFactory.java
 * Factory class to create Field venue instances.
 * Inherits from VenueFactory.
 * Implements the createVenue method to instantiate Fields.
 * Returns a new Field object with provided parameters.
 */


public class FieldFactory extends VenueFactory {
    /*
     * Creates and returns a new Field instance
     * @param name Name of the field
     * @param capacity Seating capacity of the field
     * @return Venue instance of Field
     */

     
    @Override
    public Venue createVenue(String name, int capacity) {
        return new Field(name, capacity);
    }
    
}