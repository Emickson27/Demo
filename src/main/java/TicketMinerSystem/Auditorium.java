/*
 * Auditorium.java
 * Represents an Auditorium venue with specific attributes and behaviors.
 */


public class Auditorium extends Venue{

    
    /*
     * Constructor to initialize Auditorium with name and capacity
     * @param name Name of the auditorium
     * @param capacity Seating capacity of the auditorium
     */
    public Auditorium(String name, int capacity){
        super(name, capacity);
    }

    
    /*
     * Display details of the Auditorium
     * Overrides the displayDetails method from Venue class
     */

     
    @Override
    public void displayDetails(){
        System.out.print("Auditorium: "+ getName()+
                            "Capacity: "+ getCapacity());
    }
    
}