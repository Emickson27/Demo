
/*
 * Sport.java
 * Represents a Sport event with specific attributes and behaviors.
 * UPDATED TO SUPPORT NEGATIVE QUANTITIES FOR TICKET CANCELLATION.
 */

public class Sport extends Event {

    private int vipSeatsSold;
    private int goldSeatsSold;
    private int silverSeatsSold;
    private int bronzeSeatsSold;
    private int generalSeatsSold;

    public Sport(int eventId, String name, String date, String time,
                 double vipPrice, double goldPrice, double silverPrice,
                 double bronzePrice, double generalPrice,
                 boolean isFireworksIncluded, Venue venue) {
        super(eventId, name, date, time,
              vipPrice, goldPrice, silverPrice,
              bronzePrice, generalPrice,
              isFireworksIncluded, venue);
    }

    // ======================
    // SEAT GETTERS
    // ======================
    @Override public int getVipSeatsSold() { return vipSeatsSold; }
    @Override public int getGoldSeatsSold() { return goldSeatsSold; }
    @Override public int getSilverSeatsSold() { return silverSeatsSold; }
    @Override public int getBronzeSeatsSold() { return bronzeSeatsSold; }
    @Override public int getGeneralSeatsSold() { return generalSeatsSold; }

    // ======================
    // SAFE SELL / RETURN SEATS
    // ======================

    @Override
    public void sellVipSeats(int q) {
        vipSeatsSold = Math.max(0, vipSeatsSold + q);
    }

    @Override
    public void sellGoldSeats(int q) {
        goldSeatsSold = Math.max(0, goldSeatsSold + q);
    }

    @Override
    public void sellSilverSeats(int q) {
        silverSeatsSold = Math.max(0, silverSeatsSold + q);
    }

    @Override
    public void sellBronzeSeats(int q) {
        bronzeSeatsSold = Math.max(0, bronzeSeatsSold + q);
    }

    @Override
    public void sellGeneralSeats(int q) {
        generalSeatsSold = Math.max(0, generalSeatsSold + q);
    }
}

