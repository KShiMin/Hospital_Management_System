package org.bee.hms.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class OpUtils {
    public static void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public static int optionsInput(Scanner scan, int min, int max) {
        String option;
        while (true) {
            System.out.println("Please select an option: ");
            option = scan.nextLine();
            try {
                int optionValue = Integer.parseInt(option);
                if (optionValue > min && optionValue < max) {
                    return optionValue;
                } else {
                    System.out.println("\nInvalid Input. Please enter a valid number within the range.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Input. Please enter a valid number.");
            }
        }
    }

    public static String convertDateToString(Date date) {
        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date customDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            return formatter.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public static String convertCommasForDataStorage(String string) {
        return string.replace(',', '$');
    }
    
    public static void printExit() {
        System.out.println("\n----------------------------------------------------------------------------");
        System.out.println("\tThank you for using Outpatient Clinic Management System");
        System.out.println("----------------------------------------------------------------------------");
    }
}
