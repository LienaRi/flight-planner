package io.codelex.flightplanner.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeFormatter {

    public static LocalDateTime formatDateTime(LocalDateTime dateTimeToFormat) {
        return LocalDateTime.parse(dateTimeToFormat.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public static LocalDate formatDate(LocalDate dateToFormat) {
        return LocalDate.parse(dateToFormat.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
