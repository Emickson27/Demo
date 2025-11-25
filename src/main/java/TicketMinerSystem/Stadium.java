/*
 * Stadium.java
 * Represents a Stadium venue with specific attributes and behaviors.
 * Inherits from the Venue class.
 * Overrides the displayDetails method to provide specific details for Stadium venues.
 */

 
public class Stadium extends Venue{// Inherits from Venue class

    // Constructor
    public Stadium(String name, int capacity){
        super(name, capacity);
    }

    @Override// Overriding the displayDetails method
    public void displayDetails(){
        System.out.print("Stadium: "+ getName()+
                            "Capacity: "+ getCapacity());
    }
    
}
