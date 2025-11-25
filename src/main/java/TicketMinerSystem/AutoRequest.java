/*
 * AutoRequest.java
 * Represents an automatic ticket purchase request.
 * Contains details such as username, event ID, ticket type, and quantity.
 * Used for processing bulk ticket purchases.
 */


public class AutoRequest {


    /*
     * Constructor to initialize AutoRequest with necessary details
     * @param username Customer's username
     * @param eventId ID of the event to purchase tickets for
     * @param ticketType Type of ticket to purchase (e.g. "VIP", "General")
     * @param quantity Number of tickets to purchase
     */


    public String username;
    public int eventId;
    public String ticketType;
    public int quantity;


    /*
     * Constructor to initialize AutoRequest with necessary details
     * @param username Customer's username
     * @param eventId ID of the event to purchase tickets for
     * @param ticketType Type of ticket to purchase (e.g. "VIP", "General")
     * @param quantity Number of tickets to purchase
     */

     
    public AutoRequest(String username, int eventId, String ticketType, int quantity) {
        this.username = username;
        this.eventId = eventId;
        this.ticketType = ticketType;
        this.quantity = quantity;
    }
}