package main.java.TicketMinerGUI;


import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.Parent;  // ADD THIS IMPORT
import java.util.List;
import java.lang.reflect.Method;

public class CustomerLoginScreen {
    private BorderPane root;
    private List<Object> customers;
    private LoginCallback onLoginSuccess;
    private Runnable onBackToMain;

    public interface LoginCallback {
        void onLoginSuccess(Object customer);
    }

    public CustomerLoginScreen(List<Object> customers, LoginCallback onLoginSuccess, Runnable onBackToMain) {
        this.customers = customers;
        this.onLoginSuccess = onLoginSuccess;
        this.onBackToMain = onBackToMain;
        createUI();
    }

    private void createUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db);");

        // Header
        Label headerLabel = new Label("CUSTOMER LOGIN");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        headerLabel.setPadding(new Insets(50, 0, 50, 0));

        // Login form
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setMaxWidth(400);
        loginBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 15; -fx-padding: 40;");

        Label titleLabel = new Label("Customer Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        styleTextField(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        styleTextField(passwordField);

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 8;");
        loginBtn.setMinWidth(200);

        Button backBtn = new Button("Back to Main");
        backBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20; -fx-background-radius: 6;");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter both username and password");
                return;
            }

            Object customer = authenticate(username, password);
            if (customer != null) {
                errorLabel.setText("");
                onLoginSuccess.onLoginSuccess(customer);
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });

        backBtn.setOnAction(e -> onBackToMain.run());

        loginBox.getChildren().addAll(titleLabel, usernameField, passwordField, loginBtn, backBtn, errorLabel);

        root.setTop(headerLabel);
        root.setCenter(loginBox);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);
    }

    private void styleTextField(TextField field) {
        field.setStyle("-fx-font-size: 14px; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 8;");
        field.setMinWidth(250);
    }

    private Object authenticate(String username, String password) {
        for (Object customer : customers) {
            try {
                // Use reflection to call getUsername() and getPassword()
                Method getUsernameMethod = customer.getClass().getMethod("getUsername");
                Method getPasswordMethod = customer.getClass().getMethod("getPassword");
                
                String custUsername = (String) getUsernameMethod.invoke(customer);
                String custPassword = (String) getPasswordMethod.invoke(customer);
                
                if (custUsername.equalsIgnoreCase(username) && custPassword.equals(password)) {
                    return customer;
                }
            } catch (Exception e) {
                System.out.println("Error during authentication: " + e.getMessage());
            }
        }
        return null;
    }

    public Parent getRoot() {
        return root;
    }
}
