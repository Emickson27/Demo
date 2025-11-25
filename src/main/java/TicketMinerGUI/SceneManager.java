package main.java.TicketMinerGUI;


import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;  // ADD THIS IMPORT
import javafx.scene.control.Alert;
import java.util.List;

public class SceneManager {
    private Stage primaryStage;
    private Scene currentScene;
    
    private List<Object> events;
    private List<Object> customers;
    private List<Object> invoices;

    public SceneManager(Stage primaryStage, List<Object> events, List<Object> customers, List<Object> invoices) {
        this.primaryStage = primaryStage;
        this.events = events;
        this.customers = customers;
        this.invoices = invoices;
    }

    public void showMainSelection() {
        MainSelectionScreen mainScreen = new MainSelectionScreen();
        
        mainScreen.setOnCustomerSelected(() -> showCustomerLogin());
        mainScreen.setOnAdminSelected(() -> showAdminLogin());
        mainScreen.setOnExitSelected(() -> primaryStage.close());
        
        switchScene(mainScreen.getRoot());
    }

    public void showCustomerLogin() {
        CustomerLoginScreen loginScreen = new CustomerLoginScreen(
            customers, 
            (Object customer) -> showCustomerDashboard(customer),
            () -> showMainSelection()
        );
        
        switchScene(loginScreen.getRoot());
    }

    public void showAdminLogin() {
        showAlert("Admin Login", "Admin functionality coming soon!");
    }

    public void showCustomerDashboard(Object customer) {
        CustomerDashboard dashboard = new CustomerDashboard(
            customer, events, invoices, 
            () -> showMainSelection()
        );
        switchScene(dashboard.getRoot());
    }

    private void switchScene(Parent root) {
        currentScene = new Scene(root);
        primaryStage.setScene(currentScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}