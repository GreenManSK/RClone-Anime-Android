package net.greenmanov.android.rcloneanime.persistance;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static String persist(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(time);

    }

    @TypeConverter
    public static LocalDateTime load(String persisted) {
        if (persisted == null) {
            return null;
        }
        return LocalDateTime.parse(persisted, DATE_TIME_FORMATTER);
    }
}