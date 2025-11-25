/*
 * Venue.java
 * Abstract class representing a general venue with common attributes and behaviors.
 * Serves as a base class for specific venue types like Stadium, OpenAir, and Outdoor.
 */


public abstract class Venue {
    // Common attributes
    private String name;
    private int capacity;

    public Venue(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    //abstract method (required)
    public abstract void displayDetails();

    public void displayVenueDetails() {
        System.out.println("Venue: " + name + " (Capacity: " + capacity + ")");
    }
}

