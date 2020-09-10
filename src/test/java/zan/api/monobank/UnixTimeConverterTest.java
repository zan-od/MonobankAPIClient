package zan.api.monobank;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UnixTimeConverterTest {

    @Test
    public void toDateTime_1970() {
        assertEquals(LocalDateTime.of(1970, 1, 1, 0, 0, 0), UnixTimeConverter.toDateTime(0));
    }

    @Test
    public void toUnixTime_1970() {
        assertEquals(0, UnixTimeConverter.toUnixTime(LocalDateTime.of(1970, 1, 1, 0, 0, 0)));
    }

    @Test
    public void toDateTime_Millenium() {
        assertEquals(LocalDateTime.of(2000, 1, 1, 15, 37, 59), UnixTimeConverter.toDateTime(946741079));
    }

    @Test
    public void toUnixTime_Millenium() {
        assertEquals(946741079, UnixTimeConverter.toUnixTime(LocalDateTime.of(2000, 1, 1, 15, 37, 59)));
    }
}