/*
 * EventFinanceManager.java
 * Manages financial calculations for events, including revenue and profit.
 * Provides static methods to compute various financial metrics.
 * Calculates revenue for different ticket types.
 * Estimates expected profit based on event capacity and average ticket price.
 * Calculates current profit by subtracting operating costs from total revenue.
 * Uses a fixed base operating cost for calculations.
 */

public class EventFinanceManager {

    private static final double BASE_OPERATING_COST = 681500;

    // Calculates total revenue for an event
    public static double calculateTotalRevenue(Event e) {
        return getVipRevenue(e) + getGoldRevenue(e) + getSilverRevenue(e)
                + getBronzeRevenue(e) + getGeneralRevenue(e);
    }

    public static double getVipRevenue(Event e) {
        return e.getVipSeatsSold() * e.getVipPrice();
    }

    public static double getGoldRevenue(Event e) {
        return e.getGoldSeatsSold() * e.getGoldPrice();
    }

    public static double getSilverRevenue(Event e) {
        return e.getSilverSeatsSold() * e.getSilverPrice();
    }

    public static double getBronzeRevenue(Event e) {
        return e.getBronzeSeatsSold() * e.getBronzePrice();
    }

    public static double getGeneralRevenue(Event e) {
        return e.getGeneralSeatsSold() * e.getGeneralPrice();
    }

    public static double calculateExpectedProfit(Event e) {
        if (e.getVenue() == null) return 0;
        int capacity = e.getVenue().getCapacity();
        double avgPrice = (e.getVipPrice() + e.getGoldPrice() + e.getSilverPrice()
                + e.getBronzePrice() + e.getGeneralPrice()) / 5;
        return avgPrice * capacity - BASE_OPERATING_COST;
    }

    public static double calculateCurrentProfit(Event e) {
        return calculateTotalRevenue(e) - BASE_OPERATING_COST;
    }
}

