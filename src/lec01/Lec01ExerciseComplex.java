package lec01;

import java.util.*;

public class Lec01ExerciseComplex {

    public static void main(String[] args) {

        // Establish a way to receive input as space separated words.
        Scanner scan = new Scanner(System.in);

        // Read the first input as an integer count of sighting records.

        int count = scan.nextInt();

        // Read entries into an array of entries.
        // Keep track of longest and shortest along the way.
        // Build list of shapes as each shape first encountered.

        UFOEntry[] entries = new UFOEntry[count];
        UFOEntry longest = null;
        UFOEntry shortest = null;
        List<String> shapes_encountered = new ArrayList<String>();

        for (int i=0; i<count; i++) {
            entries[i] = readEntryFromScanner(scan);
            if ((longest == null) || (entries[i].duration >= longest.duration)) {
                longest = entries[i];
            }

            if ((shortest == null) || (entries[i].duration <= longest.duration)) {
                shortest = entries[i];
            }

            if (!shapes_encountered.contains(entries[i].shape)) {
                shapes_encountered.add(entries[i].shape);
            }

        }

        // Report information about longest and shortest.
        assert longest != null;
        System.out.println("Longest sighting:");
        System.out.println("  When: " + makeDateTimeString(longest.timestamp));
        System.out.println("  Shape: " + longest.shape);
        System.out.println("  Where: " + makeLatLongString(longest.location));
        System.out.println();

        assert shortest != null;
        System.out.println("Shortest sighting:");
        System.out.println("  When: " + makeDateTimeString(shortest.timestamp));
        System.out.println("  Shape: " + shortest.shape);
        System.out.println("  Where: " + makeLatLongString(shortest.location));
        System.out.println();

        // Report averages by shape.

        for (String shape : shapes_encountered) {
            double duration_avg = averageIntegerList(extractDurations(filterByShape(entries, shape)));
            Location lat_long_avg = averageLocation(extractLocations(filterByShape(entries, shape)));
            System.out.println("Averages for " + shape + ":");
            System.out.println("  Duration: " + String.format("%.2f", duration_avg));
            System.out.println("  Location: " + makeLatLongString(lat_long_avg));
            System.out.println();
        }
    }

    // Helper functions:

    // readEntryFromScanner
    // Provided scanner object with beginning of UFO entry as next input.
    // Reads entry data, creates and returns new entry object with data.
    static UFOEntry readEntryFromScanner(Scanner scan) {
        UFOEntry e = new UFOEntry();

        e.timestamp = readDateTimeFromScanner(scan);
        e.shape = scan.next();
        e.duration = scan.nextInt();
        e.location = readLocationFromScanner(scan);

        return e;
    }

    // readDateTimeFromScanner
    // Provided scanner object with date and time as next input.
    // Reads date and time data, creates and returns new DateTime object with data.
    static DateTime readDateTimeFromScanner(Scanner scan) {
        DateTime ts = new DateTime();
        ts.date = readDateFromScanner(scan);
        ts.time = readTimeFromScanner(scan);
        return ts;
    }

    // readTimeFromScanner
    // Provided scanner object with time as next input.
    // Reads time data, creates and return new time object with data.
    static Time readTimeFromScanner(Scanner scan) {
        Time t = new Time();
        String time_str = scan.next();
        String[] time_components = time_str.split(":");
        t.hours = Integer.parseInt(time_components[0]);
        t.minutes = Integer.parseInt(time_components[1]);

        return t;
    }

    // readDateFromScanner
    // Provided scanner object with date as next input.
    // Reads date data, creates and returns new date object with data.
    static Date readDateFromScanner(Scanner scan) {
        Date d = new Date();
        String date_str = scan.next();
        String[] data_components = date_str.split("/");
        d.month = Integer.parseInt(data_components[0]);
        d.day = Integer.parseInt(data_components[1]);
        d.year = Integer.parseInt(data_components[2]);

        return d;
    }

    // readLocationFromScanner
    // Provided scanner object with latitude and longitude as next input.
    // Reads lat and long data, creates and returns new Location object with data.
    static Location readLocationFromScanner(Scanner scan) {
        Location loc = new Location();
        loc.latitude = scan.nextDouble();
        loc.longitude = scan.nextDouble();
        return loc;
    }

    // makeLatLongString
    // Given a Location object, creates and returns a formatted String
    // like so: "(lat, long)" with location latitude and longitude
    // expressed as real number rounded to the second decimal point.
    static String makeLatLongString(Location loc) {
        return String.format("(%.2f, %.2f)", loc.latitude, loc.longitude);
    }

    // makeDateTimeString
    // Given a DateTime object, creates and returns a formatted String
    // like so: "mm/dd/yy hh::mm" with month, day, year, hour, and minute
    // information expressed as integers in appropriate places.
    static String makeDateTimeString(DateTime dt) {
        return String.format("%d/%d/%d %d:%02d", dt.date.month, dt.date.day, dt.date.year, dt.time.hours, dt.time.minutes);
    }

    // averageLocation
    // Given a list of Location objects, returns a new Location object representing the
    // average latitude and longitude in the list.
    private static Location averageLocation(List<Location> locations) {
        double lat_sum = 0.0;
        double long_sum = 0.0;

        for (Location loc : locations) {
            lat_sum += loc.latitude;
            long_sum += loc.longitude;
        }

        Location avg_location = new Location();
        avg_location.latitude = lat_sum / locations.size();
        avg_location.longitude = long_sum / locations.size();

        return avg_location;
    }

    // averageIntegerList
    // Given a list of integers, returns its average as a double.
    private static double averageIntegerList(List<Integer> values) {
        int sum = 0;
        for (int i : values) {
            sum += i;
        }
        return ((double) sum) / ((double) values.size());
    }

    // extractLocations
    // Given a list of UFOEntry objects, returns a new list of
    // Location objects extracted from the UFO entries.
    private static List<Location> extractLocations(List<UFOEntry> entries) {
        List<Location> locations = new ArrayList<Location>();
        for (UFOEntry e : entries) {
            locations.add(e.location);
        }
        return locations;
    }

    // extractDurations
    // Given a list of UFOEntry objects, returns a new list of
    // integers representing the durations extracted from the UFO entries.
    private static List<Integer> extractDurations(List<UFOEntry> entries) {
        List<Integer> durations = new ArrayList<Integer>();
        for (UFOEntry e : entries) {
            durations.add(e.duration);
        }
        return durations;
    }

    // filterByShape
    // Given an array of UFOEntry objects and a shape filter string value,
    // returns a list of UFOEntry objects from the array with a shape that matches the shape filter.
    private static List<UFOEntry> filterByShape(UFOEntry[] entries, String shape) {
        List<UFOEntry> filtered = new ArrayList<UFOEntry>();

        for (UFOEntry e : entries) {
            if (e.shape.equals(shape)) {
                filtered.add(e);
            }
        }
        return filtered;
    }
}

// Object defined for use above.

// UFOEntry
class UFOEntry {
    DateTime timestamp;
    String shape;
    int duration;
    Location location;
}

// LatLong
class Location {
    double latitude;
    double longitude;
}

// TimeStamp
class DateTime {
    Date date;
    Time time;
}

// Date
class Date {
    int month;
    int day;
    int year;
}

// Time
class Time {
    int hours;
    int minutes;
}