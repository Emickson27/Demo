
import java.util.Objects;

/*
 * Event.java
 * Abstract class representing a generic event in the Ticket Miner System.
 * NOW UPDATED to track Company Fees (Convenience, Service, Charity)
 */

public abstract class Event implements Displayable {

    private int eventId;
    private String name;
    private String date;
    private String time;
    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double generalPrice;
    private Venue venue;
    private double totalTaxCollected;
    private boolean isFireworksIncluded;

    // ============================================
    //       NEW — COMPANY FEE TRACKING
    // ============================================
    private double convenienceFeeCollected = 0;
    private double serviceFeeCollected = 0;
    private double charityFeeCollected = 0;

    public Event(int eventId, String name, String date, String time,
                 double vipPrice, double goldPrice, double silverPrice,
                 double bronzePrice, double generalPrice,
                 boolean isFireworksIncluded, Venue venue) {

        this.eventId = eventId;
        this.name = Objects.requireNonNullElse(name, "Unknown");
        this.date = Objects.requireNonNullElse(date, "TBD");
        this.time = Objects.requireNonNullElse(time, "TBD");
        this.vipPrice = vipPrice;
        this.goldPrice = goldPrice;
        this.silverPrice = silverPrice;
        this.bronzePrice = bronzePrice;
        this.generalPrice = generalPrice;
        this.isFireworksIncluded = isFireworksIncluded;
        this.venue = venue;
    }

    // ============================================================
    // GETTERS & SETTERS
    // ============================================================
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public double getVipPrice() { return vipPrice; }
    public void setVipPrice(double vipPrice) { this.vipPrice = vipPrice; }
    public double getGoldPrice() { return goldPrice; }
    public void setGoldPrice(double goldPrice) { this.goldPrice = goldPrice; }
    public double getSilverPrice() { return silverPrice; }
    public void setSilverPrice(double silverPrice) { this.silverPrice = silverPrice; }
    public double getBronzePrice() { return bronzePrice; }
    public void setBronzePrice(double bronzePrice) { this.bronzePrice = bronzePrice; }
    public double getGeneralPrice() { return generalPrice; }
    public void setGeneralPrice(double generalPrice) { this.generalPrice = generalPrice; }
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
    public double getTotalTaxCollected() { return totalTaxCollected; }

    public void addTax(double tax) { this.totalTaxCollected += tax; }

    public boolean isFireworksIncluded() { return isFireworksIncluded; }
    public void setFireworksIncluded(boolean fireworksIncluded) { isFireworksIncluded = fireworksIncluded; }

    // ============================================================
    // NEW — FEE TRACKING METHODS (Used during purchases)
    // ============================================================
    public void addConvenienceFee(double amount) {
        this.convenienceFeeCollected += amount;
    }

    public void addServiceFee(double amount) {
        this.serviceFeeCollected += amount;
    }

    public void addCharityFee(double amount) {
        this.charityFeeCollected += amount;
    }

    public double getConvenienceFeeCollected() {
        return convenienceFeeCollected;
    }

    public double getServiceFeeCollected() {
        return serviceFeeCollected;
    }

    public double getCharityFeeCollected() {
        return charityFeeCollected;
    }

    // ============================================================
    // ABSTRACT METHODS — Seat Management
    // ============================================================
    public abstract int getVipSeatsSold();
    public abstract int getGoldSeatsSold();
    public abstract int getSilverSeatsSold();
    public abstract int getBronzeSeatsSold();
    public abstract int getGeneralSeatsSold();

    public abstract void sellVipSeats(int quantity);
    public abstract void sellGoldSeats(int quantity);
    public abstract void sellSilverSeats(int quantity);
    public abstract void sellBronzeSeats(int quantity);
    public abstract void sellGeneralSeats(int quantity);

    // ============================================================
    // PRICE BY TYPE
    // ============================================================
    public double getPriceByType(String type) {
        String t = type == null ? "" : type.trim().toLowerCase();
        return switch (t) {
            case "vip" -> vipPrice;
            case "gold" -> goldPrice;
            case "silver" -> silverPrice;
            case "bronze" -> bronzePrice;
            case "general" -> generalPrice;
            default -> throw new IllegalArgumentException("Invalid ticket type");
        };
    }

    // SELL SEATS BY TYPE
    public void sellSeatsByType(String type, int qty) {
        String t = type == null ? "" : type.trim().toLowerCase();
        switch (t) {
            case "vip" -> sellVipSeats(qty);
            case "gold" -> sellGoldSeats(qty);
            case "silver" -> sellSilverSeats(qty);
            case "bronze" -> sellBronzeSeats(qty);
            case "general" -> sellGeneralSeats(qty);
        }
    }

    // ============================================================
    // AVAILABILITY CHECKER
    // ============================================================
    public boolean hasAvailableSeats(String ticketType, int quantity) {
        if (venue == null) return false;

        int capacity = venue.getCapacity();
        String t = ticketType.toLowerCase();

        int vipLimit    = (int)(capacity * 0.05);
        int goldLimit   = (int)(capacity * 0.10);
        int silverLimit = (int)(capacity * 0.15);
        int bronzeLimit = (int)(capacity * 0.20);
        int generalLimit = capacity - (vipLimit + goldLimit + silverLimit + bronzeLimit);

        return switch (t) {
            case "vip" -> (vipLimit - getVipSeatsSold()) >= quantity;
            case "gold" -> (goldLimit - getGoldSeatsSold()) >= quantity;
            case "silver" -> (silverLimit - getSilverSeatsSold()) >= quantity;
            case "bronze" -> (bronzeLimit - getBronzeSeatsSold()) >= quantity;
            case "general" -> (generalLimit - getGeneralSeatsSold()) >= quantity;
            default -> false;
        };
    }

    public int getTotalSeatsSold() {
        return getVipSeatsSold() + getGoldSeatsSold()
                + getSilverSeatsSold() + getBronzeSeatsSold()
                + getGeneralSeatsSold();
    }

    // ============================================================
    // DISPLAY
    // ============================================================
    @Override
    public void displaySummary() {
        System.out.println("[" + eventId + "] "
                + getClass().getSimpleName() + " - " + name
                + " | " + date + " " + time
                + " | Venue: " + (venue != null ? venue.getName() : "N/A")
                + " | Fireworks: " + (isFireworksIncluded ? "Yes" : "No"));
    }

    @Override
    public void displayDetailedInfo() {
        System.out.println("========== EVENT DETAILS ==========");
        System.out.println("Event ID: " + eventId);
        System.out.println("Name: " + name);
        System.out.println("Date: " + date + " at " + time);

        System.out.println("\n--- Company Fees Collected ---");
        System.out.printf("Convenience Fees: $%.2f%n", convenienceFeeCollected);
        System.out.printf("Service Fees:     $%.2f%n", serviceFeeCollected);
        System.out.printf("Charity Fees:     $%.2f%n", charityFeeCollected);
        System.out.printf("Total Fees:       $%.2f%n",
                convenienceFeeCollected + serviceFeeCollected + charityFeeCollected);

        System.out.println("==================================");
    }

    @Override
    public String toString() {
        return "Event ID: " + eventId
                + " - " + getClass().getSimpleName()
                + " - " + name + " (" + date + " at " + time + ")";
    }
}

