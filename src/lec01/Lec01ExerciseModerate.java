package lec01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lec01ExerciseModerate {

    public static void main(String[] args) {

        // Establish a way to receive input as space separated words.
        Scanner s = new Scanner(System.in);

        // Read the first input as an integer count of sighting records.

        int count = s.nextInt();

        // Create arrays for data.

        int[] months = new int[count];
        int[] days = new int[count];
        int[] years = new int[count];
        int[] hours = new int[count];
        int[] minutes = new int[count];
        String[] shapes = new String[count];
        int[] durations = new int[count];
        double[] latitudes = new double[count];
        double[] longitudes = new double[count];

        // Create empty list for shapes encountered.

        List<String> shapes_encountered = new ArrayList<String>();

        // Read data for each entry. Update shape list if shape not
        // previously encountered.

        for (int i = 0; i < count; i++) {

            readDateAndTime(s, i, months, days, years, hours, minutes);
            shapes[i] = s.next();
            durations[i] = s.nextInt();
            latitudes[i] = s.nextDouble();
            longitudes[i] = s.nextDouble();

            if (!shapes_encountered.contains(shapes[i])) {
                shapes_encountered.add(shapes[i]);
            }
        }

        // Report on shortest and longest durations.

        int longest_sighting_index = findMaxIndex(durations);
        int shortest_sighting_index = findMinIndex(durations);

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

        // Report averages by shape.

        for (String shape : shapes_encountered) {
            int shape_count = countByShape(shapes, shape);
            int duration_sum = sumIntsByShape(durations, shapes, shape);
            double latitude_sum = sumDoublesByShape(latitudes, shapes, shape);
            double longitude_sum = sumDoublesByShape(longitudes, shapes, shape);

            System.out.println("Averages for " + shape + ":");
            System.out.println("  Duration: " + String.format("%.2f", ((double) duration_sum) / ((double) shape_count)));
            System.out.println("  Location: " + String.format("(%.2f, %.2f)", latitude_sum / ((double) shape_count), longitude_sum / ((double) shape_count)));
            System.out.println();
        }
    }

    // Helper functions:

    // readDateAndTime(scanner, index
    //                 month_array, day_array, year_array,
    // 		           hour_array, minute_array)
    //
    // Provided a scanner object with next expected input
    // to be in "mm/dd/yy hh:mm" format.
    // Also provided index value and component arrays.
    //
    // Parses component values for month, day, year, hour,
    // and minute from input and sets the corresponding values
    // in appropriate arrays at index provided.

    private static void readDateAndTime(Scanner scan, int index,
                                        int[] months, int[] days, int[] years,
                                        int[] hours, int[] minutes) {
        String date_str = scan.next();
        String[] date_components = date_str.split("/");
        months[index] = Integer.parseInt(date_components[0]);
        days[index] = Integer.parseInt(date_components[1]);
        years[index] = Integer.parseInt(date_components[2]);

        String time_str = scan.next();
        String[] time_components = time_str.split(":");
        hours[index] = Integer.parseInt(time_components[0]);
        minutes[index] = Integer.parseInt(time_components[1]);
    }

    // sumIntsByShape(values, shapes, shape_filter)
    //
    // Passed an integer array of values with a
    // corresponding array of shape strings.
    // Also passed a specific shape string to filter by.
    //
    // Sums the entries of the integer array that have a
    // corresponding shape that matches the filter shape string.
    // Returns overall sum.

    private static int sumIntsByShape(int[] values, String[] shapes, String shape_filter) {
        int sum = 0;
        for (int i=0; i<values.length; i++) {
            if (shapes[i].equals(shape_filter)) {
                sum += values[i];
            }
        }
        return sum;
    }

    // sumDoublesByShape(values, shapes, shape_filter)
    //
    // Passed a double array with a corresponding array of shape strings.
    // Also passed a specific shape string to filter by.
    //
    // Sums the entries of the double array that have a
    // corresponding shape that matches the filter shape string.
    // Returns overall sum.
    private static double sumDoublesByShape(double[] values, String[] shapes, String shape_filter) {
        double sum = 0.0;
        for (int i=0; i<values.length; i++) {
            if (shapes[i].equals(shape_filter)) {
                sum += values[i];
            }
        }
        return sum;
    }

    // countByShape(shapes, shape_filter)
    //
    // Passed an array of shape strings and a specific
    // shape string to filter by.
    //
    // Returns the number of times the shape
    // appears in the array.
    private static int countByShape(String[] shapes, String shape_filter) {
        int count = 0;
        for (String s : shapes) {
            if (s.equals(shape_filter)) {
                count++;
            }
        }
        return count;
    }

    // findMaxIndex(values)
    //
    // Passed an array of integers.
    //
    // Returns the index associated with the largest value in the array.
    // If largest value appears more than once, index value of last
    // occurrence (i.e, greatest index value) is returned.
    private static int findMaxIndex(int[] values) {
        int max = values[0];
        int max_index = 0;
        for (int i=0; i<values.length; i++) {
            if (values[i] >= max) {
                max = values[i];
                max_index = i;
            }
        }
        return max_index;
    }

    // findMinIndex(values)
    //
    // Passed an array of integers.
    //
    // Returns the index associated with the smallest value in the array.
    // If smallest value appears more than once, index value of last
    // occurrence (i.e., greatest index value) is returned.
    private static int findMinIndex(int[] values) {
        int min = values[0];
        int min_index = 0;
        for (int i=0; i<values.length; i++) {
            if (values[i] <= min) {
                min = values[i];
                min_index = i;
            }
        }
        return min_index;
    }
}
