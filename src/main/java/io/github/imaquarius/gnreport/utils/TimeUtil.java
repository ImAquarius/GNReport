package io.github.imaquarius.gnreport.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String formatTimestampWithTimeZone(long timestamp, ZoneId zoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId);
        // Append the time zone information
        return dateTime.format(formatter) + " " + zoneId.toString();
    }
}
