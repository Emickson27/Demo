/*
 * Displayable.java
 * Interface for displayable entities in the Ticket Miner System.
 * Defines methods for displaying summary and detailed information.
 * Implemented by classes that need to present information to users.
 * Facilitates consistent display behavior across different entities.
 * Supports extensibility for future displayable types.
 * Enhances user experience by standardizing information presentation.
 */

public interface Displayable {
    /* 
     * Displays a summary of the entity
     * @return void
     */
    
    void displaySummary();
    void displayDetailedInfo();
}
