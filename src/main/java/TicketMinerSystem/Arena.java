/*
 * Arena.java
 * Represents an Arena venue with specific attributes and behaviors.
 */

public class Arena extends Venue{ // Inherits from Venue class
    

    /*
     * Constructor to initialize Arena with name and capacity
     * @param name Name of the arena
     * @param capacity Seating capacity of the arena
     */


    public Arena(String name, int capacity){
        super(name, capacity);
    }


    /*
     * Display details of the Arena
     * Overrides the displayDetails method from Venue class
     */

     
    @Override 
    public void displayDetails(){
        System.out.print("Arena: "+ getName()+
                            "Capacity: "+ getCapacity());
    }
    
}
