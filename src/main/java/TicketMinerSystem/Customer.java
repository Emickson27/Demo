/*
 * Customer.java
 * Represents a customer in the Ticket Miner System.
 * Contains customer details and methods to manage customer data.
 * Handles membership status and purchase history.
 * Includes methods for financial transactions and ticket purchases.
 * Overrides equals and hashCode for proper comparison and hashing.
 * Maintains total savings for the customer.
 * Provides getters and setters for all attributes.
 * Includes methods to check affordability and deduct money.
 * Tracks the number of concerts purchased by the customer.
 * Manages Ticket Miner membership status.
 * Stores username and password for authentication.
 * Facilitates customer identification by ID.
 * Supports incrementing concert purchase count.
 * Ensures data integrity through validation in financial methods.
 * Allows access to customer details for display and processing.
 * Facilitates customer management within the Ticket Miner System.
 * Enables tracking of customer spending and savings.
 * Supports customer-related operations in the system.
 * Enhances user experience by managing customer data effectively.
 */


public class Customer {
    /*
     * @param id Unique identifier for the customer
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param moneyAvailable Amount of money the customer has available for purchases
     * @param concertsPurchased Number of concerts the customer has purchased tickets for
     * @param ticketMinerMemebership Whether the customer has a Ticket Miner membership
     * @param username Customer's username for login
     * @param password Customer's password for login
     * @param totalSaved Total amount of money the customer has saved through discounts
     * Used for managing customer data and transactions within the Ticket Miner System
     */


    private int id;
    private String firstName;
    private String lastName;
    private double moneyAvailable;
    private int concertsPurchased;
    private boolean ticketMinerMemebership; // NOTE: kept original field name to avoid breaking CSV/API
    private String username;
    private String password;
    private double totalSaved;

    public Customer(int id, String firstName, String lastName, double moneyAvailable,
                    int concertsPurchased, boolean ticketMinerMemebership, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyAvailable = moneyAvailable;
        this.concertsPurchased = concertsPurchased;
        this.ticketMinerMemebership = ticketMinerMemebership;
        this.username = username;
        this.password = password;
    }

    // Getters/Setters
    /*
     * Getters and setters for customer attributes
     * @param id Unique identifier for the customer
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     * @param moneyAvailable Amount of money the customer has available for purchases
     * @param concertsPurchased Number of concerts the customer has purchased tickets for
     * @param ticketMinerMemebership Whether the customer has a Ticket Miner membership
     * @param username Customer's username for login
     * @param password Customer's password for login
     * Used for accessing and modifying customer data within the Ticket Miner System
     * @return void
     */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public double getMoneyAvailable() { return moneyAvailable; }
    public void setMoneyAvailable(double moneyAvailable) { this.moneyAvailable = moneyAvailable; }
    public int getConcertsPurchased() { return concertsPurchased; }
    public void setConcertsPurchased(int concertsPurchased) { this.concertsPurchased = concertsPurchased; }
    public boolean isTicketMinerMemebership() { return ticketMinerMemebership; }
    public void setTicketMinerMemebership(boolean ticketMinerMemebership) { this.ticketMinerMemebership = ticketMinerMemebership; }
    public boolean hasTicketMinerMembership() { return ticketMinerMemebership; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public void addConcertPurchase() { this.concertsPurchased++; }
    public boolean canAfford(double amount) { return this.moneyAvailable >= amount; }


    /*
     * Deducts the specified amount from the customer's available money if affordable.
     * @param amount Amount to deduct
     * @return void
     */


    public void deductMoney(double amount) {
        if (canAfford(amount)) {
            this.moneyAvailable -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public void addMoney(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot add negative money.");
        this.moneyAvailable += amount;
    }


    public double getTotalSaved() { return totalSaved; }
    public void addToTotalSaved(double amount) { this.totalSaved += amount; }


    /*
     * Overrides equals to compare customers based on their ID.
     * @param o Object to compare
     * @return true if IDs are equal, false otherwise
     * Overrides hashCode to generate hash based on customer ID.
     * @return hash code of the customer ID
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer)) return false;
        return ((Customer) o).id == this.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }
}
