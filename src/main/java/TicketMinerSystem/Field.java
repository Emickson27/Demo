/*
 * Field.java
 * Represents a Field venue with specific attributes and behaviors.
 * Inherits from the Venue class.
 * Handles display of field details.
 */
public class Field extends Venue {
    /*
     * Constructor to initialize Field with name and capacity
     * @param name Name of the field
     * @param capacity Seating capacity of the field
     */
    public Field(String name, int capacity) {
        super(name, capacity);
    }

    @Override
    public void displayDetails() {
        System.out.println("Field: " + getName() + " (Capacity: " + getCapacity() + ")");
    }
}
