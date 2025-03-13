package org.bee.hms.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateConverter{

    /**
     * Converts a string representation of a date in "dd-MM-yyyy" format into a
     * {@link Date} object.
     * If the conversion fails, it prints an error message and returns {@code null}.
     *
     * @param dateString The date string to be converted.
     * @return The corresponding {@link Date} object if the conversion is
     *         successful, or {@code null} if it fails.
     */
    public static Date customDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            return formatter.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}
