package main.java.TicketMinerGUI;



import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.Parent;  // ADD THIS IMPORT

public class MainSelectionScreen {
    private BorderPane root;
    private Runnable onCustomerSelected;
    private Runnable onAdminSelected;
    private Runnable onExitSelected;

    public MainSelectionScreen() {
        createUI();
    }

    private void createUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db);");

        // Header
        Label headerLabel = new Label("TICKETMINER SYSTEM");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        headerLabel.setPadding(new Insets(50, 0, 50, 0));

        // Center content
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        Label welcomeLabel = new Label("Welcome to TicketMiner");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        // Buttons
        Button customerBtn = createSelectionButton("Customer Login", "#27ae60");
        Button adminBtn = createSelectionButton("Admin Login", "#e74c3c");
        Button exitBtn = createSelectionButton("Exit System", "#95a5a6");

        customerBtn.setOnAction(e -> onCustomerSelected.run());
        adminBtn.setOnAction(e -> onAdminSelected.run());
        exitBtn.setOnAction(e -> onExitSelected.run());

        centerBox.getChildren().addAll(welcomeLabel, customerBtn, adminBtn, exitBtn);

        root.setTop(headerLabel);
        root.setCenter(centerBox);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);
    }

    private Button createSelectionButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; " +
                       "-fx-text-fill: white; " +
                       "-fx-font-size: 18px; " +
                       "-fx-font-weight: bold; " +
                       "-fx-padding: 15 30; " +
                       "-fx-background-radius: 10;");
        button.setMinWidth(200);
        return button;
    }

    public Parent getRoot() {
        return root;
    }

    // Setters for callbacks
    public void setOnCustomerSelected(Runnable onCustomerSelected) {
        this.onCustomerSelected = onCustomerSelected;
    }

    public void setOnAdminSelected(Runnable onAdminSelected) {
        this.onAdminSelected = onAdminSelected;
    }

    public void setOnExitSelected(Runnable onExitSelected) {
        this.onExitSelected = onExitSelected;
    }
}
