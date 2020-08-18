package com.splitbills.logging;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlainTextFormatterTest {

    @Test
    public void format() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 5, 3, 20, 50);
        String message = "Some message";
        LogRecord logRecord = new LogRecord(date, Level.INFO, message, new IOException(message));
        Formatter formatter = new PlainTextFormatter();

        String expectedDate = "2020-06-05 03:20:50 ";
        String expectedLevel = "INFO ";
        String expectedException = " java.io.IOException: ";
        String expected = expectedDate + expectedLevel + message + expectedException + message
                + System.lineSeparator();
        assertEquals(expected, formatter.format(logRecord));
    }

    @Test
    public void formatRecordWithNullValues() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 5, 3, 20, 50);
        LogRecord logRecord = new LogRecord(date, Level.INFO, null, null);
        Formatter formatter = new PlainTextFormatter();
        String expectedDate = "2020-06-05 03:20:50 ";
        String expectedLevel = "INFO";
        String expected = expectedDate + expectedLevel + System.lineSeparator();
        assertEquals(expected, formatter.format(logRecord));

    }
}
