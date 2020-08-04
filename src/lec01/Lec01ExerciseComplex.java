package lec01;

import java.util.*;

public class Lec01ExerciseComplex {

    public static void main(String[] args) {

        // Establish a way to receive input as space separated words.
        Scanner scan = new Scanner(System.in);

        // Read the first input as an integer count of sighting records.

        int count = scan.nextInt();

        // Read entries into storage.
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


        assert longest != null;
        System.out.println("Longest sighting:");
        System.out.println("  When: " + makeTimestampString(longest.timestamp));
        System.out.println("  Shape: " + longest.shape);
        System.out.println("  Where: " + makeLatLongString(longest.location));
        System.out.println();

        assert shortest != null;
        System.out.println("Shortest sighting:");
        System.out.println("  When: " + makeTimestampString(shortest.timestamp));
        System.out.println("  Shape: " + shortest.shape);
        System.out.println("  Where: " + makeLatLongString(shortest.location));
        System.out.println();

        // Report averages by shape.

        for (String shape : shapes_encountered) {
            double duration_avg = averageInteger(extractDurations(filterByShape(entries, shape)));
            LatLong lat_long_avg = averageLatLong(extractLocations(filterByShape(entries, shape)));
            System.out.println("Averages for " + shape + ":");
            System.out.println("  Duration: " + String.format("%.2f", duration_avg));
            System.out.println("  Location: " + makeLatLongString(lat_long_avg));
            System.out.println();
        }
    }

    static UFOEntry readEntryFromScanner(Scanner scan) {
        UFOEntry e = new UFOEntry();

        e.timestamp = readTimeStampFromScanner(scan);
        e.shape = scan.next();
        e.duration = scan.nextInt();
        e.location = readLatLongFromScanner(scan);

        return e;
    }

    static TimeStamp readTimeStampFromScanner(Scanner scan) {
        TimeStamp ts = new TimeStamp();
        ts.date = readDateFromScanner(scan);
        ts.time = readTimeFromScanner(scan);
        return ts;
    }

    static Time readTimeFromScanner(Scanner scan) {
        Time t = new Time();
        String time_str = scan.next();
        String[] time_components = time_str.split(":");
        t.hours = Integer.parseInt(time_components[0]);
        t.minutes = Integer.parseInt(time_components[1]);

        return t;
    }

    static Date readDateFromScanner(Scanner scan) {
        Date d = new Date();
        String date_str = scan.next();
        String[] data_components = date_str.split("/");
        d.month = Integer.parseInt(data_components[0]);
        d.day = Integer.parseInt(data_components[1]);
        d.year = Integer.parseInt(data_components[2]);

        return d;
    }

    static LatLong readLatLongFromScanner(Scanner scan) {
        LatLong loc = new LatLong();
        loc.latitude = scan.nextDouble();
        loc.longitude = scan.nextDouble();
        return loc;
    }

    static String makeLatLongString(LatLong loc) {
        return String.format("(%.2f, %.2f)", loc.latitude, loc.longitude);
    }

    static String makeTimestampString(TimeStamp ts) {
        return String.format("%d/%d/%d %d:%02d", ts.date.month, ts.date.day, ts.date.year, ts.time.hours, ts.time.minutes);
    }

    private static LatLong averageLatLong(List<LatLong> values) {
        double lat_sum = 0.0;
        double long_sum = 0.0;

        for (LatLong loc : values) {
            lat_sum += loc.latitude;
            long_sum += loc.longitude;
        }

        LatLong avg_lat_long = new LatLong();
        avg_lat_long.latitude = lat_sum / values.size();
        avg_lat_long.longitude = long_sum / values.size();

        return avg_lat_long;
    }

    private static double averageInteger(List<Integer> values) {
        int sum = 0;
        for (int i : values) {
            sum += i;
        }
        return ((double) sum) / ((double) values.size());
    }

    private static List<LatLong> extractLocations(List<UFOEntry> entries) {
        List<LatLong> locations = new ArrayList<LatLong>();
        for (UFOEntry e : entries) {
            locations.add(e.location);
        }
        return locations;
    }

    private static List<Integer> extractDurations(List<UFOEntry> entries) {
        List<Integer> durations = new ArrayList<Integer>();
        for (UFOEntry e : entries) {
            durations.add(e.duration);
        }
        return durations;
    }

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

// Object definitions used above.

class UFOEntry {
    TimeStamp timestamp;
    String shape;
    int duration;
    LatLong location;
}

class LatLong {
    double latitude;
    double longitude;
}

class TimeStamp {
    Date date;
    Time time;
}

class Date {
    int month;
    int day;
    int year;
}

class Time {
    int hours;
    int minutes;
}