package ptithcm.device_maintenance.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class DateHelper {
    public static LocalDate parseStringAsLocalDate(String dateValue) {
        Pattern pattern = Pattern.compile("([A-Za-z]{3} [A-Za-z]{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2} GMT[+-]\\d{4})");
        Matcher matcher = pattern.matcher(dateValue);

        if (matcher.find()) {
            String extractedDateString = matcher.group(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z");

            ZonedDateTime zonedDateTime = ZonedDateTime.parse(extractedDateString, formatter);
            return zonedDateTime.toLocalDate();
        } else {
            throw new DateTimeParseException("Invalid date format", dateValue, 0);
        }
    }

    public static LocalDate convertDateAsLocalDate(Date dateValue) {
        return dateValue
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
