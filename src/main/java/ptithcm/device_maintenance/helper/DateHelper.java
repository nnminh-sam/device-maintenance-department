package ptithcm.device_maintenance.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DateHelper {
    private static final List<DateTimeFormatter> formats = List.of(
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMTZ'", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH),
            DateTimeFormatter.ISO_DATE_TIME,
            DateTimeFormatter.ISO_DATE,
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
    );

    public static Optional<LocalDate> parseStringAsLocalDate(String date) {
        for (var format : formats) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, format);
                return Optional.of(parsedDate);
            } catch (Exception e) {
                // Log the exception and continue to the next format
                // System.err.println("Failed to parse date with format: " + format + " due to: " + e.getMessage());
            }
        }
        return Optional.empty();
    }

    public static LocalDate convertDateAsLocalDate(Date dateValue) {
        return dateValue
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
