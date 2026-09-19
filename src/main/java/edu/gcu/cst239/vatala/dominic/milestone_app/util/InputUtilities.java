package edu.gcu.cst239.vatala.dominic.milestone_app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class used to centralize console input operations.
 * The class provides reusable methods for reading and validating
 * common input types.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class InputUtilities {

    /**
     * Scanner used for all console input operations.
     * The scanner is intentionally not closed because closing it
     * would also close System.in.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a line of text from the console.
     *
     * @param prompt the message displayed before reading input
     * @return the text entered by the user
     */
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Reads an integer from the console.
     * The user is prompted again until a valid integer is entered.
     *
     * @param prompt the message displayed before reading input
     * @return the integer entered by the user
     */
    public int readInt(String prompt) {
        int result = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println(prompt);
                result = Integer.parseInt(scanner.nextLine().trim());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid number.");
            }
        }

        return result;
    }

    /**
     * Reads an integer within a required range.
     * The user is prompted again until the value falls within
     * the minimum and maximum values.
     *
     * @param prompt the message displayed before reading input
     * @param min the minimum acceptable value, inclusive
     * @param max the maximum acceptable value, inclusive
     * @return the valid integer entered by the user
     */
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println(prompt);
                result = Integer.parseInt(scanner.nextLine().trim());

                if (result >= min && result <= max) {
                    isValid = true;
                } else {
                    System.out.println(
                            "Invalid input. Please enter a number between "
                                    + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid number.");
            }
        }

        return result;
    }

    /**
     * Reads a double value from the console.
     * The user is prompted again until a valid decimal value is entered.
     *
     * @param prompt the message displayed before reading input
     * @return the double value entered by the user
     */
    public double readDouble(String prompt) {
        double result = 0.0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println(prompt);
                result = Double.parseDouble(scanner.nextLine().trim());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid decimal number.");
            }
        }

        return result;
    }

    /**
     * Reads a double value within a required range.
     * The user is prompted again until the value falls within
     * the minimum and maximum values.
     *
     * @param prompt the message displayed before reading input
     * @param min the minimum acceptable value, inclusive
     * @param max the maximum acceptable value, inclusive
     * @return the valid double entered by the user
     */
    public double readDouble(String prompt, double min, double max) {
        double result = 0.0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println(prompt);
                result = Double.parseDouble(scanner.nextLine().trim());

                if (result >= min && result <= max) {
                    isValid = true;
                } else {
                    System.out.println(
                            "Invalid input. Please enter a number between "
                                    + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid decimal number.");
            }
        }

        return result;
    }

    /**
     * Reads a boolean value from the console.
     * Only true or false values are accepted.
     *
     * @param prompt the message displayed before reading input
     * @return the boolean value entered by the user
     */
    public boolean readBoolean(String prompt) {
        boolean result = false;
        boolean isValid = false;

        while (!isValid) {
            System.out.println(prompt + " (true/false):");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("true") || input.equals("false")) {
                result = Boolean.parseBoolean(input);
                isValid = true;
            } else {
                System.out.println(
                        "Invalid input. Please enter 'true' or 'false'.");
            }
        }

        return result;
    }

    /**
     * Reads a LocalDate using a specified date format.
     * The user is prompted again until a valid date is entered.
     *
     * @param prompt the message displayed before reading input
     * @param pattern the required date format, such as yyyy-MM-dd
     * @return the date entered by the user
     */
    public LocalDate readLocalDate(String prompt, String pattern) {
        LocalDate result = null;
        boolean isValid = false;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(pattern);

        while (!isValid) {
            try {
                System.out.println(
                        prompt + " (format: " + pattern + ")");

                result = LocalDate.parse(
                        scanner.nextLine().trim(),
                        formatter);

                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid input. Please enter a date in the format "
                                + pattern + ".");
            }
        }

        return result;
    }

    /**
     * Reads multiple strings from the console.
     * Input continues until the user enters a blank line.
     *
     * @param prompt the message displayed before reading the list
     * @return a list containing the strings entered by the user
     */
    public List<String> readStringList(String prompt) {
        List<String> list = new ArrayList<>();

        System.out.println(
                prompt + " (enter blank line to finish):");

        while (true) {
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                break;
            }

            list.add(value);
        }

        return list;
    }
}