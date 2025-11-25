package main.java.TicketMinerGUI;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class TicketMinerApp extends Application {
    
    private Stage primaryStage;
    private SceneManager sceneManager;
    
    private List<Object> events;
    private List<Object> customers;
    private List<Object> invoices;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Initialize with your actual backend data
        loadBackendData();
        
        this.sceneManager = new SceneManager(primaryStage, events, customers, invoices);
        
        initializeApplication();
    }
    
    private void loadBackendData() {
        try {
            this.events = new ArrayList<>();
            this.customers = new ArrayList<>();
            this.invoices = new ArrayList<>();
            
            // Try to load real data first
            if (!loadRealData()) {
                // Fallback to demo data
                loadDemoData();
            }
            
            System.out.println("Loaded " + customers.size() + " customers and " + events.size() + " events");
            
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            loadDemoData();
        }
    }
    
    private boolean loadRealData() {
        try {
            // Try to load events using your EventLoader
            Class<?> eventLoaderClass = Class.forName("EventLoader");
            Method loadEventsMethod = eventLoaderClass.getMethod("loadEvents", String.class);
            Object eventsResult = loadEventsMethod.invoke(null, "data/EventListPA3(Sheet1).csv");
            
            if (eventsResult instanceof List) {
                this.events = (List<Object>) eventsResult;
            }
            
            // Try to load customers using your CustomerLoader
            Class<?> customerLoaderClass = Class.forName("CustomerLoader");
            Method loadCustomersMethod = customerLoaderClass.getMethod("loadCustomers", String.class);
            Object customersResult = loadCustomersMethod.invoke(null, "data/CustomerListPA3(Sheet1).csv");
            
            if (customersResult instanceof List) {
                this.customers = (List<Object>) customersResult;
            }
            
            return !events.isEmpty() && !customers.isEmpty();
            
        } catch (Exception e) {
            System.out.println("Could not load real data: " + e.getMessage());
            return false;
        }
    }
    
    private void loadDemoData() {
        try {
            // Create demo customer
            Class<?> customerClass = Class.forName("Customer");
            Object demoCustomer = customerClass.getConstructor(int.class, String.class, String.class, 
                    double.class, int.class, boolean.class, String.class, String.class)
                    .newInstance(1, "Jesus", "Munoz", 5849.13, 0, true, "jesus", "password");
            
            customers.add(demoCustomer);
            
            System.out.println("Using demo data with customer: Jesus");
            
        } catch (Exception e) {
            System.out.println("Could not create demo data: " + e.getMessage());
            // Create empty lists as fallback
            customers = new ArrayList<>();
            events = new ArrayList<>();
            invoices = new ArrayList<>();
        }
    }
    
    private void initializeApplication() {
        primaryStage.setTitle("TicketMiner System");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        
        // Show main selection screen first
        sceneManager.showMainSelection();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
