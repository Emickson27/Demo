

import java.util.*;


/*
 * MenuDisplay.java
 * Class to display the main menu and handle user interactions.
 */


public class MenuDisplay {
    /*
     * Attributes
     * @param scanner Scanner object for user input
     * @param events List of events in the system
     * @param customers List of customers in the system
     * @param invoices List of invoices in the system
     */


    private final Scanner scanner;
    private final List<Event> events;
    private final List<Customer> customers;
    private final List<Invoice> invoices;

    public MenuDisplay(List<Event> events, List<Customer> customers, List<Invoice> invoices) {
        this.scanner = new Scanner(System.in);
        this.events = events;
        this.customers = customers;
        this.invoices = invoices;
    }


    /*
     * Displays the main menu and handles user choices
     * @return void
     */
    
    public void showMainMenu() {
        while (true) {
            System.out.println("\n===== TICKETMINER MAIN MENU =====");
            System.out.println("1. Login as Customer");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> customerLogin();
                case "2" -> adminLogin();
                case "3" -> {
                    System.out.println("Exiting system...");
                    Logger.log("System exited.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===================== CUSTOMER LOGIN =====================
    private void customerLogin() {
        System.out.println("\n===== CUSTOMER LOGIN =====");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username) && c.getPassword().equals(password)) {
                System.out.println("Welcome back, " + c.getFirstName() + "!");
                Logger.log("Customer logged in: " + c.getUsername());
                CustomerMenu customerMenu = new CustomerMenu(events, customers, invoices);
                customerMenu.showMenu(c);
                return;
            }
        }
        System.out.println(" Invalid credentials.");
    }

    // ===================== ADMIN LOGIN =====================
    private void adminLogin() {
        System.out.println("\n===== ADMIN LOGIN =====");
            System.out.println("Admin access granted.");
            Logger.log("Admin logged in.");
            AdminMenu.showMenu(events, customers, invoices);
    }
}
