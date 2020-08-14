package com.splitbills.logging;

import java.time.format.DateTimeFormatter;

public class PlainTextFormatter implements Formatter {

    private final static String FORMAT = "%s %s %s %s %n";
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String format(LogRecord logRecord) {
        String date = logRecord
                .getDate()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        return String.format(FORMAT, date, logRecord.getLevel(), logRecord.getMessage(),
                logRecord.getThrowable());
    }
}
