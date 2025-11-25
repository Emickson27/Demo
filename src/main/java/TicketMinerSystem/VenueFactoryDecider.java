/*
 * VenueFactoryDecider.java
 * Decides and returns the appropriate VenueFactory based on the venue type input.
 */

 
public class VenueFactoryDecider {
    public static VenueFactory getFactory(String venueTypeInput) {
        if (venueTypeInput == null) return null;
        String t = venueTypeInput.replaceAll("\\s+", "").toLowerCase();
        return switch (t) {
            case "arena" -> new ArenaFactory();
            case "auditorium" -> new AuditoriumFactory();
            case "stadium" -> new StadiumFactory();
            case "openair" -> new OpenAirFactory();
            case "field" -> new FieldFactory();
            case "outdoor" -> new OutdoorFactory();
            default -> null;
        };
    }
}

