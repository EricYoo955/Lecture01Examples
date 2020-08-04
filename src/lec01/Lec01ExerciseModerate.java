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
            int duration_sum = sumByShape(durations, shapes, shape);
            double latitude_sum = sumByShape(latitudes, shapes, shape);
            double longitude_sum = sumByShape(longitudes, shapes, shape);

            System.out.println("Averages for " + shape + ":");
            System.out.println("  Duration: " + String.format("%.2f", ((double) duration_sum) / ((double) shape_count)));
            System.out.println("  Location: " + String.format("(%.2f, %.2f)", latitude_sum / ((double) shape_count), longitude_sum / ((double) shape_count)));
            System.out.println();
        }
    }

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

    private static int sumByShape(int[] values, String[] shapes, String shape) {
        int sum = 0;
        for (int i=0; i<values.length; i++) {
            if (shapes[i].equals(shape)) {
                sum += values[i];
            }
        }
        return sum;
    }

    private static double sumByShape(double[] values, String[] shapes, String shape) {
        double sum = 0.0;
        for (int i=0; i<values.length; i++) {
            if (shapes[i].equals(shape)) {
                sum += values[i];
            }
        }
        return sum;
    }

    private static int countByShape(String[] shapes, String shape) {
        int count = 0;
        for (String s : shapes) {
            if (s.equals(shape)) {
                count++;
            }
        }
        return count;
    }

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
