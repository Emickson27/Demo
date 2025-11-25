/*
 * OpenAir.java
 * Represents an OpenAir venue with specific attributes and behaviors.
 * Inherits from the Venue class.
 * Overrides the displayDetails method to provide specific details for OpenAir venues.
 */

public class OpenAir extends Venue{ // Subclass of Venue

    // Constructor
    public OpenAir(String name, int capacity){ 
        super(name, capacity);
    }

    @Override
    public void displayDetails(){
        System.out.print("OpenAir: "+ getName()+
                            "Capacity: "+ getCapacity());
    }
    
}
