package lec01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lec01ExerciseSimplest {

    public static void main(String[] args) {

        // Establish a way to receive input as space separated words.
        Scanner s = new Scanner(System.in);

        // Read the first input as an integer count of sighting records.

        int count = s.nextInt();

        // Create arrays for each component element of UFO sighting entry
        // The size of the array is given by the count of records just read.
        // Create a separate array for each part of an entry:
        // month, day, year, hour, minute, shape, duration, latitude, longitude

        int[] months = new int[count];
        int[] days = new int[count];
        int[] years = new int[count];
        int[] hours = new int[count];
        int[] minutes = new int[count];
        String[] shapes = new String[count];
        int[] durations = new int[count];
        double[] latitudes = new double[count];
        double[] longitudes = new double[count];

        // Create array for possible shapes and populate with known shapes.
        String[] shape_list = {
                "chevron", "cigar", "circle", "cone", "cylinder",
                "delta", "diamond", "disk", "egg", "fireball",
                "flare", "flash", "hexagon", "light", "pyramid",
                "sphere", "teardrop", "triangle"};

        // Use a loop to read the data into the arrays.
        // The ith entry of each array holds the value for the ith UFO sighting.

        for (int i=0; i<count; i++) {
            // To read each entry:
            // Get the first word as the date string.
            String date_str = s.next();

            // Separate the date string on the "/" character.
            String[] date_components = date_str.split("/");

            // Convert each component to an integer and store in the appropriate array.
            months[i] = Integer.parseInt(date_components[0]);
            days[i] = Integer.parseInt(date_components[1]);
            years[i] = Integer.parseInt(date_components[2]);

            // Get the next word as the time string.
            String time_str = s.next();
            // Separate the time string on the ":" character.
            String[] time_components = time_str.split(":");
            // Convert each component to an integer and store in the appropriate array.
            hours[i] = Integer.parseInt(time_components[0]);
            minutes[i] = Integer.parseInt(time_components[1]);

            // Get the next word as the shape and store in the appropriate array.
            shapes[i] = s.next();

            // Get the next word as the duration understood as an integer and store.
            durations[i] = s.nextInt();

            // Get the next word as the latitude understood as a real number and store.
            latitudes[i] = s.nextDouble();

            // Get the next word as the longitude understood as a real number and store.
            longitudes[i] = s.nextDouble();
        }

        // Go through duration array and find index values
        // associated with longest and shortest UFO sightings.
        //
        // Set up variables to keep track of longest/shortest duration
        // and associated index value. Initialize these as the values
        // for entry 0.

        int longest_sighting_index = 0;
        int longest_sighting_duration = durations[0];

        int shortest_sighting_index = 0;
        int shortest_sighting_duration = durations[0];

        // Loop through your duration array.
        for (int i=0; i<count; i++) {
            // Compare each value to longest/shortest so far.
            // If longer/shorter, update longest/shortest value and index.

            if (durations[i] >= longest_sighting_duration) {
                longest_sighting_duration = durations[i];
                longest_sighting_index = i;
            }

            if (durations[i] <= shortest_sighting_duration) {
                shortest_sighting_duration = durations[i];
                shortest_sighting_index = i;
            }
        }

        // Print output about longest and shortest sightings.

        System.out.println("Longest sighting:");
        System.out.println("  When: " + String.format("%d/%d/%d %d:%d", months[longest_sighting_index], days[longest_sighting_index], years[longest_sighting_index], hours[longest_sighting_index], minutes[longest_sighting_index]));
        System.out.println("  Shape: " + shapes[longest_sighting_index]);
        System.out.println("  Where: " + String.format("(%.2f, %.2f)", latitudes[longest_sighting_index], longitudes[longest_sighting_index]));
        System.out.println();

        System.out.println("Shortest sighting:");
        System.out.println("  When: " + String.format("%d/%d/%d %d:%d", months[shortest_sighting_index], days[shortest_sighting_index], years[shortest_sighting_index], hours[shortest_sighting_index], minutes[shortest_sighting_index]));
        System.out.println("  Shape: " + shapes[shortest_sighting_index]);
        System.out.println("  Where: " + String.format("(%.2f, %.2f)", latitudes[shortest_sighting_index], longitudes[shortest_sighting_index]));
        System.out.println();

        // Calculate the average duration, latitude, and longitude for each shape.

        // Create arrays to hold sum of durations, latitudes, and longitudes
        // Each array should be size of shape list.
        // The ith value in each array corresponds to the ith shape in the shape list.
        int[] duration_sums = new int[shape_list.length];
        double[] latitude_sums = new double[shape_list.length];
        double[] longitude_sums = new double[shape_list.length];

        // Create one more array to hold the count for each shape.
        // Again, ith entry corresponds to count of ith shape in shape_list.
        int[] shape_counts = new int[shape_list.length];

        // For each entry...
        for (int i=0; i<count; i++) {
            //   Update the duration, latitude, and longitude sum for the
            //   shape of the entry by...
            //     Finding the index value of the matching shape in
            //     the shape list.
            int shape_index = 0;
            while (!shape_list[shape_index].equals(shapes[i])) {
                shape_index += 1;
            }

            //     Adding one to the corresponding shape count.
            shape_counts[shape_index] += 1;

            //     Adding the duration, latitude, and longitude
            //     to their respective corresponding values.
            duration_sums[shape_index] += durations[i];
            latitude_sums[shape_index] += latitudes[i];
            longitude_sums[shape_index] += longitudes[i];
        }

        // Produce report for each shape

        // For each shape in the shape list...
        for (int i=0; i<shape_list.length; i++) {

            //     Produce report for that shape if shape count is at least one.
            if (shape_counts[i] != 0) {
                String shape = shape_list[i];

                // Calculate average duration, latitude, and longitude by
                // dividing sum for shape by shape count.
                double avg_duration = ((double) duration_sums[i]) / ((double) shape_counts[i]);
                double avg_latitude = latitude_sums[i] / ((double) shape_counts[i]);
                double avg_longitude = longitude_sums[i]/ ((double) shape_counts[i]);

                System.out.println("Averages for " + shape + ":");
                System.out.println("  Duration: " + String.format("%.2f", avg_duration));
                System.out.println("  Location: " + String.format("(%.2f, %.2f)", latitude_sums[i] / ((double) shape_counts[i]), longitude_sums[i] / ((double) shape_counts[i])));
                System.out.println();
            }
        }
    }
}
