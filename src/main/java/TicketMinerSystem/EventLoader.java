import java.io.*;
import java.util.*;


/*
 * EventLoader.java
 * Class to load event data from a CSV file.
 * Parses the CSV and creates Event objects.
 * Handles errors gracefully and logs actions.
 * Expected CSV format:
 * EventID,EventType,Name,Date,Time,VIPPrice,GoldPrice,SilverPrice,BronzePrice,GeneralAdmissionPrice,VenueName,VenueType,Capacity,IsFireworksIncluded
 */
public class EventLoader {

    /*
     * Loads events from a CSV file and returns a list of Event objects.
     * @param fileName Path to the CSV file
     * @return List of Event objects
     */

    public static List<Event> loadEvents(String fileName) {
        List<Event> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) throw new IOException("CSV file is empty");

            String[] headers = headerLine.trim().split("\\s*,\\s*");
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    String columnName = headers[i].trim().toLowerCase().replace(" ", "");
                    String columnValue = values[i].trim();
                    row.put(columnName, columnValue);
                }

                int eventId = (int) Double.parseDouble(row.getOrDefault("eventid", "0").trim());
                String eventType = row.getOrDefault("eventtype", "Generic");
                String name = row.getOrDefault("name", "Unknown");
                String date = row.getOrDefault("date", "TBD");
                String time = row.getOrDefault("time", "TBD");

                double vipPrice = parseDouble(row.get("vipprice"));
                double goldPrice = parseDouble(row.get("goldprice"));
                double silverPrice = parseDouble(row.get("silverprice"));
                double bronzePrice = parseDouble(row.get("bronzeprice"));
                double generalAdmissionPrice = parseDouble(row.get("generaladmissionprice"));

                String venueName = row.getOrDefault("venuename", "Unknown Venue");
                String venueTypeRaw = row.getOrDefault("venuetype", "Generic");
                String venueType = venueTypeRaw.replaceAll("\\s+", "").toLowerCase(); // normalize
                int capacity = (int) parseDouble(row.get("capacity"));
                boolean isFireworksIncluded = Boolean.parseBoolean(row.getOrDefault("isfireworksincluded", "false"));

                VenueFactory venueFactory = VenueFactoryDecider.getFactory(venueType);
                Venue venue = null;
                if (venueFactory != null) {
                    venue = venueFactory.createVenue(venueName, capacity);
                } else {
                    System.out.println("Unknown venue type: " + venueTypeRaw);
                    Logger.log("Unknown venue type in CSV: " + venueTypeRaw);
                }

                EventFactory factory = EventFactoryDecider.getFactory(eventType);
                if (factory != null) {
                    Event event = factory.createEvent(eventId, name, date, time,
                            vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice,isFireworksIncluded, venue);
                    events.add(event);
                } else {
                    System.out.println("Unknown event type: " + eventType);
                    Logger.log("Unknown event type in CSV: " + eventType);
                }
            }

            Logger.log("Loaded " + events.size() + " events from " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            Logger.log("Error reading events CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric format in event file: " + e.getMessage());
            Logger.log("Invalid numeric format in events CSV: " + e.getMessage());
        }

        return events;
    }

    // Local tiny helper (keeps redundancy down without creating new utility classes)
    private static double parseDouble(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.parseDouble(s.trim());
    }
}
