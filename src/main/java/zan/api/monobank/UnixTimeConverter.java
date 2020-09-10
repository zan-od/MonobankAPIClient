package zan.api.monobank;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * UNIX time converter
 * @author azlatov
 */
public class UnixTimeConverter {

    /**
     * Converts UNIX time to local time (UTC timezone)
     * @param unixTime UNIX time
     * @return local time
     */
    public static LocalDateTime toDateTime(long unixTime) {
        Instant instant = Instant.ofEpochSecond(unixTime);
        return instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * Converts local time (UTC timezone) to UNIX time
     * @param dateTime local time
     * @return UNIX time
     */
    public static long toUnixTime(LocalDateTime dateTime) {
        return dateTime.atOffset(ZoneOffset.UTC).toEpochSecond();
    }

}
