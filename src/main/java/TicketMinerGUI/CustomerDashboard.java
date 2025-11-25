package main.java.TicketMinerGUI;



import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.Parent;  // ADD THIS IMPORT
import java.util.List;
import java.lang.reflect.Method;

public class CustomerDashboard {
    private BorderPane root;
    private Object currentCustomer;
    private List<Object> events;
    private List<Object> invoices;
    private Runnable onLogout;

    public CustomerDashboard(Object customer, List<Object> events, List<Object> invoices, Runnable onLogout) {
        this.currentCustomer = customer;
        this.events = events;
        this.invoices = invoices;
        this.onLogout = onLogout;
        createUI();
    }

    private void createUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        createHeader();
        createContent();
    }

    private void createHeader() {
        HBox headerBox = new HBox();
        headerBox.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");
        headerBox.setAlignment(Pos.CENTER_LEFT);

        try {
            // Use reflection to get customer details
            Method getFirstNameMethod = currentCustomer.getClass().getMethod("getFirstName");
            Method getMoneyAvailableMethod = currentCustomer.getClass().getMethod("getMoneyAvailable");
            
            String firstName = (String) getFirstNameMethod.invoke(currentCustomer);
            double balance = (Double) getMoneyAvailableMethod.invoke(currentCustomer);

            Label welcomeLabel = new Label("Welcome, " + firstName + "!");
            welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

            Label balanceLabel = new Label("Your current balance is: $" + String.format("%.2f", balance));
            balanceLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 16px;");

            VBox userInfo = new VBox(5, welcomeLabel, balanceLabel);
            HBox.setHgrow(userInfo, Priority.ALWAYS);

            Button logoutBtn = new Button("Logout");
            logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 16;");
            logoutBtn.setOnAction(e -> onLogout.run());

            headerBox.getChildren().addAll(userInfo, logoutBtn);

        } catch (Exception e) {
            System.out.println("Error creating header: " + e.getMessage());
            Label errorLabel = new Label("Welcome, Customer!");
            errorLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
            headerBox.getChildren().add(errorLabel);
        }

        root.setTop(headerBox);
    }

    private void createContent() {
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        // Menu Title
        Label menuTitle = new Label("CUSTOMER MENU");
        menuTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Separator lines
        HBox separator1 = new HBox();
        separator1.setStyle("-fx-background-color: #bdc3c7; -fx-pref-height: 2; -fx-pref-width: 300;");
        
        HBox separator2 = new HBox();
        separator2.setStyle("-fx-background-color: #bdc3c7; -fx-pref-height: 2; -fx-pref-width: 300;");

        // Menu Options
        VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setMaxWidth(400);

        Button viewEventsBtn = createMenuButton("1. View Events");
        Button purchaseTicketBtn = createMenuButton("2. Purchase Ticket");
        Button viewInvoicesBtn = createMenuButton("3. View My Invoices");
        Button cancelTicketBtn = createMenuButton("4. Cancel Ticket Purchase");
        Button logoutMenuBtn = createMenuButton("5. Logout");

        viewEventsBtn.setOnAction(e -> showEvents());
        purchaseTicketBtn.setOnAction(e -> showPurchaseTicket());
        viewInvoicesBtn.setOnAction(e -> showInvoices());
        cancelTicketBtn.setOnAction(e -> showCancelTicket());
        logoutMenuBtn.setOnAction(e -> onLogout.run());

        menuBox.getChildren().addAll(
            viewEventsBtn, purchaseTicketBtn, viewInvoicesBtn, 
            cancelTicketBtn, separator1, logoutMenuBtn
        );

        contentBox.getChildren().addAll(menuTitle, separator2, menuBox);
        root.setCenter(contentBox);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #3498db; " +
                       "-fx-text-fill: white; " +
                       "-fx-font-size: 16px; " +
                       "-fx-font-weight: bold; " +
                       "-fx-padding: 15 30; " +
                       "-fx-background-radius: 8;");
        button.setMinWidth(250);
        return button;
    }

    private void showEvents() {
        showAlert("View Events", "Events functionality coming soon!");
    }

    private void showPurchaseTicket() {
        showAlert("Purchase Ticket", "Purchase ticket functionality coming soon!");
    }

    private void showInvoices() {
        showAlert("View Invoices", "View invoices functionality coming soon!");
    }

    private void showCancelTicket() {
        showAlert("Cancel Ticket", "Cancel ticket functionality coming soon!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getRoot() {
        return root;
    }
}
