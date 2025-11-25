/*
 * Outdoor.java
 * Represents an Outdoor venue with specific attributes and behaviors.
 * Inherits from the Venue class.
 * Overrides the displayDetails method to provide specific details for Outdoor venues.
 */

 
public class Outdoor extends Venue {
    public Outdoor(String name, int capacity) {
        super(name, capacity);
    }

    @Override
    public void displayDetails() {
        System.out.println("Outdoor Venue: " + getName() + " (Capacity: " + getCapacity() + ")");
    }
}
